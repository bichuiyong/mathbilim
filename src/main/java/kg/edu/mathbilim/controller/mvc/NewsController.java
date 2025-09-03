package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.components.SubscriptionModelPopulator;
import kg.edu.mathbilim.dto.news.CreateNewsDto;
import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("news")
@RequiredArgsConstructor
public class NewsController {
    private static final String newsDto = "newsDto";
    private static final String redirect = "redirect:/news";
    private final NewsService newsService;
    private final UserService userService;
    private final SubscriptionModelPopulator subscriptionModelPopulator;

    @GetMapping()
    public String all(@RequestParam(required = false) String query,
                      @RequestParam(value = "page", defaultValue = "0") int page,
                      @RequestParam(value = "size", defaultValue = "10") int size,
                      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                      @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                      @RequestParam(value = "language", defaultValue = "ru", required = false) String lang,
                      Authentication auth,
                      Model model
    ) {
        Page<NewsDto> newsPage = newsService.getNewsByLang(query, page, size, sortBy, sortDirection, lang);

        model.addAttribute("news", newsPage);
        model.addAttribute("query", query);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("currentLang", lang);
        subscriptionModelPopulator.addSubscriptionAttributes(auth, NotificationEnum.NEWS, model);
        return "news/news";
    }

    @GetMapping("{id}")
    public String news(
            @PathVariable long id,
            Model model,
            Principal principal
    ) {
        model.addAttribute("currentUser", principal != null ? userService.getUserByEmail(principal.getName()) : null);
        model.addAttribute(newsDto, newsService.getNewsById(id));
        return "news/news-detail";
    }

    @GetMapping("create")
    public String createNews(Model model) {
        CreateNewsDto createNewsDto = CreateNewsDto.builder()
                .news(NewsDto.builder().build())
                .build();
        model.addAttribute(newsDto, createNewsDto);
        return "news/news-create";
    }

    @PostMapping("create")
    public String createNewsPost(@Valid @ModelAttribute("newsDto") CreateNewsDto newsDto,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            FieldError attachmentError = bindingResult.getFieldError("attachments");
            if (attachmentError != null) {
                model.addAttribute("attachmentError", attachmentError.getDefaultMessage());
            }

            FieldError mainImageError = bindingResult.getFieldError("image");
            if (mainImageError != null) {
                model.addAttribute("imageError", mainImageError.getDefaultMessage());
            }

            return "news/news-create";
        }

        newsService.create(newsDto);
        return redirect;
    }


    @PreAuthorize("@newsSecurity.isOwner(#id, principal.username)")
    @GetMapping("update")
    public String updateNews(@RequestParam("id") long id,
                             Model model) {
        model.addAttribute(newsDto, newsService.getById(id));
        return "media/update_news";
    }

    @PostMapping("update")
    public String updateNewsPost(@RequestParam("id") long id,
                                 @Valid @ModelAttribute("newsDto") CreateNewsDto newsDto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "media/update_news";
        }
//        newsService.updateNews(newsDto, id);
        return redirect;
    }

    @PreAuthorize("@newsSecurity.isOwner(#id, principal.username) or hasAuthority('ADMIN') or  hasAuthority('SUPER_ADMIN') ")
    @PostMapping("delete/{id}")
    public String deleteNews(@PathVariable("id") long id) {
        newsService.delete(id);
        return redirect;
    }

}

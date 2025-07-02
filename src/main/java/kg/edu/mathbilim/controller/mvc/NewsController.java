package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.components.SubscriptionModelPopulator;
import kg.edu.mathbilim.dto.news.CreateNewsDto;
import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("news")
@RequiredArgsConstructor
public class NewsController {
    private static final String newsDto = "newsDto";
    private static final String redirect = "redirect:/news";
    private final NewsService newsService;
    private final SubscriptionModelPopulator subscriptionModelPopulator;

    @GetMapping()
    public String all(@RequestParam(required = false) String query,
                      @RequestParam(value = "page", defaultValue = "1") int page,
                      @RequestParam(value = "size", defaultValue = "10") int size,
                      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                      @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                      @CookieValue(value = "lang", defaultValue = "ru", required = false) String lang,
                      Authentication auth,
                      Model model
    ) {
        model.addAttribute("news", newsService.getNewsByLang(query, page, size, sortBy, sortDirection, lang));
        model.addAttribute("query", query);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("currentLang", lang);
        subscriptionModelPopulator.addSubscriptionAttributes(auth, NotificationEnum.NEWS, model);
        return "news/news";
    }

    @GetMapping("detail")
    public String news(
            @RequestParam("id") long id,
            Model model
    ) {
        model.addAttribute(newsDto, newsService.getById(id));
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
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
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

    @PreAuthorize("@newsSecurity.isOwner(#id, principal.username) or hasAuthority('ADMIN')")
    @PostMapping("delete")
    public String deleteNews(@RequestParam("id") long id) {
        newsService.delete(id);
        return redirect;
    }

}

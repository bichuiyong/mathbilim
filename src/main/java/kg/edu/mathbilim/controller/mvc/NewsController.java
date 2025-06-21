package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.news.CreateNewsDto;
import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import lombok.RequiredArgsConstructor;
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
    private final UserService userService;
    @GetMapping()
    public String all(
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt")  String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
            Model model
    ) {
        model.addAttribute("news", newsService.getNews(page, size, sortBy, sortDirection));
        return "media/news";
    }

    @GetMapping("detail")
    public String news(
            @RequestParam("id") long id,
            Model model
    ){
        model.addAttribute(newsDto, newsService.getNewsById(id));
        return "media/news_detail";
    }

    @GetMapping("create")
    public String createNews(Model model){
        CreateNewsDto createNewsDto = CreateNewsDto.builder()
                .news(NewsDto.builder().build())
                .build();
        model.addAttribute(newsDto, createNewsDto);
        return "media/create_news";
    }
    @PostMapping("create")
    public String createNewsPost(@Valid @ModelAttribute("newsDto") CreateNewsDto newsDto,
                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "media/create_news";
        }
        newsService.createNews(newsDto);
        return redirect;
    }



    @GetMapping("update")
    public String updateNews(
                                @RequestParam ("id") long id,
                                Model model){
        model.addAttribute(newsDto, newsService.getNewsById(id));
        return "media/update_news";
    }
    @PostMapping("update")
    public String updateNewsPost(
                                @RequestParam("id") long id,
                                @Valid @ModelAttribute("newsDto") CreateNewsDto newsDto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "media/update_news";
        }
        newsService.updateNews(newsDto,id);
        return redirect;
    }



    @PostMapping("delete")
    public String deleteNews(
            @RequestParam ("id") long id
    ){
        newsService.deleteById(userService.getAuthUser(),id);
        return redirect;
    }





}

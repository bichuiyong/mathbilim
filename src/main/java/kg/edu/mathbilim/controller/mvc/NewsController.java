package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.news.CreateNewsDto;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "created_time")  String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
            Model model
    ) {
        model.addAttribute(newsDto, newsService.getNewsPage(page, size, sortBy, sortDirection));
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
        model.addAttribute(newsDto, new CreateNewsDto());
        return "media/create_news";
    }
    @PostMapping("create")
    public String createNewsPost(@Valid CreateNewsDto dto,
                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "media/create_news";
        }
        newsService.createNews(dto);
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
    public String createNewsPost(
                                @RequestParam("id") long id,
                                @Valid CreateNewsDto dto,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "media/update_news";
        }
        newsService.updateNews(dto,id);
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

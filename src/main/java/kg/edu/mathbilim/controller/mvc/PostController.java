package kg.edu.mathbilim.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("mvcPost")
@RequestMapping
@RequiredArgsConstructor
public class PostController {

    @GetMapping("news")
    public String news(Model model) {
        return "media/news-page";
    }

    @GetMapping("publications")
    public String publications(Model model) {
        return "media/publication-page";
    }
}

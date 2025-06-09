package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.dto.PostDto;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/{type}")
    public String getAllPosts(Model model) {
        return "media/posts-page";
    }

    @GetMapping("/{type}/{id}")
    public String getFile(@PathVariable String type) {
        return "";
    }

}

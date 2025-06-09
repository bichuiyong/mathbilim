package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.dto.PostDto;
import kg.edu.mathbilim.enums.PostType;
import kg.edu.mathbilim.service.interfaces.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller("mvcPost")
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public String createPost(Model model) {
        model.addAttribute("post", new PostDto());
        model.addAttribute("postTypes", PostType.getAllValues());
        return "media/post-create";
    }

    @PostMapping
    public String createPost(@ModelAttribute("post") PostDto post,
                             BindingResult bindingResult,
                             @RequestParam(required = false) MultipartFile[] files,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postTypes", PostType.getAllValues());
            return "media/post-create";
        }
        PostDto dto = postService.createPost(post, files);
        return "redirect:/posts/" + dto.getType().getName() + "/" + dto.getId();
    }

    @GetMapping("news")
    public String news(Model model) {
        return "media/news-page";
    }

    @GetMapping("publications")
    public String publications(Model model) {
        return "media/publication-page";
    }
}

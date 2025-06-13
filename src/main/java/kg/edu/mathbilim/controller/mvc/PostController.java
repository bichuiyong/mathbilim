package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.service.interfaces.TranslationService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import kg.edu.mathbilim.service.interfaces.UserService;
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
    private final UserService userService;
    private final TranslationService translationService;

    @GetMapping("create")
    public String createPost(Model model) {
        model.addAttribute("user", userService.getAuthUser());
        model.addAttribute("post", new PostDto());
        model.addAttribute("postTypes", translationService.getPostTypesByLanguage());
        return "media/post-create";
    }

    @PostMapping("create")
    public String createPost(@ModelAttribute("post") @Valid PostDto post,
                             BindingResult bindingResult,
                             @RequestParam(required = false) MultipartFile[] attachments,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userService.getAuthUser());
            model.addAttribute("postTypes", translationService.getPostTypesByLanguage());
            return "media/post-create";
        }
        if (attachments == null) attachments = new MultipartFile[0];
        postService.createPost(post, attachments);
        return "redirect:/posts/";
    }

    @GetMapping("news")
    public String news(Model model) {
        return "media/news-page";
    }

    @GetMapping("publications")
    public String publications(Model model) {
        return "media/publication-page";
    }

    @GetMapping("blog")
    public String blog(Model model) {
        return "media/blog";
    }
}

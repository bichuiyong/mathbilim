package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import kg.edu.mathbilim.service.interfaces.post.PostTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller("mvcPost")
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostTypeService postTypeService;

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("postTypes", postTypeService.getPostTypesByLanguage("ru"));
        model.addAttribute("languages", Language.getLanguagesMap());
        model.addAttribute("languageEnum", Language.values());
    }

    @GetMapping("create")
    public String createPost(Model model) {
        CreatePostDto createPostDto = CreatePostDto.builder()
                .post(PostDto.builder().build())
                .build();
        model.addAttribute("createPostDto", createPostDto);
        return "media/post-create";
    }

    @PostMapping("create")
    public String createPost(@ModelAttribute("createPostDto") @Valid CreatePostDto post,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "media/post-create";
        }
        postService.createPost(post);
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

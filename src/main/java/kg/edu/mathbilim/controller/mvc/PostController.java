package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.CaptchaResponseDto;
import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import kg.edu.mathbilim.service.interfaces.post.PostTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller("mvcPost")
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final PostService postService;
    private final PostTypeService postTypeService;
    private final RestTemplate restTemplate;


    @Value("${recaptcha.secret}")
    private String secret;

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
                             BindingResult bindingResult,
                             @RequestParam("g-recaptcha-response") String captchaResponse) {

        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        assert response != null;
        if (bindingResult.hasErrors() || Boolean.FALSE.equals(response.getSuccess())) {
            return "media/post-create";
        }
        postService.createPost(post);
        return "redirect:/posts/";
    }

    @GetMapping("news")
    public String news() {
        return "media/news-page";
    }

    @GetMapping("publications")
    public String publications() {
        return "media/publication-page";
    }

    @GetMapping("blog")
    public String blog() {
        return "media/blog";
    }
}

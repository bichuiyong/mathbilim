package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.CaptchaResponseDto;
import kg.edu.mathbilim.dto.blog.CreateBlogDto;
import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller("mvcBlog")
@RequestMapping("blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final RestTemplate restTemplate;

    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Value("${recaptcha.secret}")
    private String secret;



    @PostMapping("create")
    public String createBlog(@ModelAttribute("createBlogDto") @Valid CreateBlogDto blogDto,
                             BindingResult bindingResult,
                             @RequestParam("g-recaptcha-response") String captchaResponse) {

        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        assert response != null;
        if (bindingResult.hasErrors() || Boolean.FALSE.equals(response.getSuccess())) {
            return "blogs/blog-create";
        }
        blogService.createBlog(blogDto);
        return "redirect:/blogs/";
    }

}

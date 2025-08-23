package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.components.SubscriptionModelPopulator;
import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.service.interfaces.CommentService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import kg.edu.mathbilim.service.interfaces.post.PostTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;


@Slf4j
@Controller("mvcPost")
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostTypeService postTypeService;
    private final RestTemplate restTemplate;
    private final CommentService commentService;
    private final UserService userService;
    private final SubscriptionModelPopulator subscriptionModelPopulator;


    @Value("${recaptcha.secret}")
    private String secret;

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("postTypes", postTypeService.getPostTypesByLanguage("ru"));
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
                             BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors() || post.getImage() == null || post.getImage().isEmpty()) {
            if (post.getImage() == null || post.getImage().isEmpty()) {
                model.addAttribute("imageError", "Image is required");
            }
            return "media/post-create";
        }
        postService.createPost(post);
        return "redirect:/posts";
    }


    @GetMapping
    public String posts(@RequestParam(required = false) String query,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                        @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                        @RequestParam(value = "language", defaultValue = "ru", required = false) String lang,
                        Authentication auth,
                        Model model) {

        log.info("language{}", lang);
        model.addAttribute("posts",
                postService.getPostsByStatus(
                        "APPROVED",
                        query,
                        page,
                        size,
                        sortBy,
                        sortDirection,
                        lang
                ));

        model.addAttribute("query", query);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        log.info("Current language: {}", lang);
        model.addAttribute("currentLang", lang);
        subscriptionModelPopulator.addSubscriptionAttributes(auth, NotificationEnum.POST, model);
        return "post/post-list";
    }



    @GetMapping("{postId}")
    public String detailPost(@PathVariable Long postId, Model model, Principal principal) {
        model.addAttribute("post", postService.getPostById(postId));
        model.addAttribute("currentUser", principal != null ? userService.getUserByEmail(principal.getName()) : null);
        return "post/post-detail";
    }
}

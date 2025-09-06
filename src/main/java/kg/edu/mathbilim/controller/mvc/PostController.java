package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.components.SubscriptionModelPopulator;
import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.dto.news.NewsTranslationDto;
import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.service.interfaces.CommentService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import kg.edu.mathbilim.service.interfaces.post.PostTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Locale;


@Slf4j
@Controller("mvcPost")
@RequestMapping("posts")
@RequiredArgsConstructor
public class    PostController {
    private final PostService postService;
    private final PostTypeService postTypeService;
    private final RestTemplate restTemplate;
    private final CommentService commentService;
    private final UserService userService;
    private final SubscriptionModelPopulator subscriptionModelPopulator;
    private final MessageSource messageSource;


    @Value("${recaptcha.secret}")
    private String secret;

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        Locale lan =  LocaleContextHolder.getLocale();
        model.addAttribute("postTypes", postTypeService.getPostTypesByLanguage(lan.getLanguage()));
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
                             RedirectAttributes redirectAttributes,
                              Model model) {


        if (bindingResult.hasErrors() || post.getImage() == null || post.getImage().isEmpty()) {
            FieldError attachmentError = bindingResult.getFieldError("attachments");
            if (attachmentError != null) {
                model.addAttribute("attachmentError", attachmentError.getDefaultMessage());
            }

            FieldError mainImageError = bindingResult.getFieldError("image");
            if (mainImageError != null) {
                model.addAttribute("imageError", mainImageError.getDefaultMessage());
            }

            return "media/post-create";
        }
        PostDto postDto = postService.createPost(post);

        if (postDto.getStatus() == ContentStatus.APPROVED) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Пост успешно создан и опубликован.");
        } else if (postDto.getStatus() == ContentStatus.PENDING_REVIEW) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Пост успешно создан и ожидает модерации. После одобрения будет опубликован.");
        }

        return "redirect:/posts";
    }


    @GetMapping
    public String posts(@RequestParam(required = false) String query,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                        @RequestParam(value = "sortDirection", defaultValue = "DESC") String sortDirection,
                        @RequestParam(value = "language", defaultValue = "ru", required = false) String lang,
                        Authentication auth,
                        Model model) {

        log.info("language{}", lang);
        Page<PostDto> posts = postService.getPostsForMainPostPage(
                "APPROVED",
                query,
                page,
                size,
                sortBy,
                sortDirection,
                lang
        );
        for (PostDto postDto : posts.getContent()) {
            List<PostTranslationDto> translations = postDto.getPostTranslations();
            translations.sort((a, b) -> {
                if (a.getLanguageCode().equals(lang)) return -1;
                if (b.getLanguageCode().equals(lang)) return 1;
                return 0;
            });
        }

        model.addAttribute("posts", posts);


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
        String email = (principal != null) ? principal.getName() : null;
        model.addAttribute("post", postService.getPostById(postId, email));
        model.addAttribute("currentUser", principal != null ? userService.getUserByEmail(principal.getName()) : null);
        return "post/post-detail";
    }

    @PreAuthorize("@postSecurity.isOwner(#id, principal.username) or hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        postService.delete(id);

        return "redirect:/posts";
    }
}

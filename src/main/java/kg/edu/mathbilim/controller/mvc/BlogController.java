package kg.edu.mathbilim.controller.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.edu.mathbilim.components.SubscriptionModelPopulator;
import kg.edu.mathbilim.dto.abstracts.DisplayContentDto;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import kg.edu.mathbilim.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@Controller("mvcBlog")
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final UserNotificationService userNotificationService;
    private final SubscriptionModelPopulator subscriptionModelPopulator;
    private final UserService userService;

    @GetMapping
    public String all(@RequestParam(required = false) String query,
                      @RequestParam(value = "page", defaultValue = "1") int page,
                      @RequestParam(value = "size", defaultValue = "5") int size,
                      @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                      @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                      @RequestParam(value = "lang", defaultValue = "ru", required = false) String lang,
                      Authentication authentication,
                      Model model) {
        model.addAttribute("blog",
                blogService.getBlogsByStatusForMainPage(
                        "APPROVED",
                        query,
                        page,
                        size,
                        sortBy,
                        sortDirection,
                        lang));
        model.addAttribute("query", query);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("currentLang", lang);

        subscriptionModelPopulator.addSubscriptionAttributes(authentication, NotificationEnum.BLOG, model);
        return "blog/blog-list";
    }

    @GetMapping("/create")
    public String createBlogForm(Model model) {
        BlogDto blogDto = BlogDto.builder().build();
        model.addAttribute("blogDto", blogDto);
        return "blog/blog-create";
    }

    @PostMapping("/create")
    public String createBlog(@ModelAttribute("blogDto") @Valid BlogDto blogDto,
                             BindingResult bindingResult,
                             @RequestParam(value = "mpMainImage", required = false) MultipartFile mpMainImage,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("blogDto", blogDto);
            return "blog/blog-create";
        }

        BlogDto createdBlog = blogService.create(blogDto, mpMainImage);
        return "redirect:/blog/" + createdBlog.getId();
    }

    @GetMapping("/{id}")
    public String viewBlog(@PathVariable Long id,
                           HttpServletRequest request,
                           Model model, Principal principal) {

        blogService.incrementViewCount(id);
        BlogDto blog = blogService.getDisplayBlogById(id);

        String shareUrl = UrlUtil.getBaseURL(request) + "/blog/" + id;
        model.addAttribute("blog", blog);
        log.info("Creator name {}", blog.getCreator().getName());
        model.addAttribute("shareUrl", shareUrl);
        model.addAttribute("currentUser", principal != null ? userService.getUserByEmail(principal.getName()) : null);


        return "blog/blog";
    }
}
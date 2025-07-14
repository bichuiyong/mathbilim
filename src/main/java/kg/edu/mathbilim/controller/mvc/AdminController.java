package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.service.interfaces.TranslationService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import kg.edu.mathbilim.service.interfaces.reference.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final RoleService roleService;
    private final TranslationService translationService;
    private final PostService postService;
    private final BlogService blogService;
    private final EventService eventService;
    private static final String status = "PENDING_REVIEW";

    @GetMapping
    public String index(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("types", translationService.getUserTypesByLanguage());
        return "admin/admin";
    }
    @GetMapping("contentToApprovePosts")
    public String contentToApprovePosts(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false, defaultValue = "en") String language,
            Model model) {
        Page<PostDto> page1 = postService.getPostsByStatus(status,query, page, size, sortBy, sortDirection, language);
        model.addAttribute("posts", page1.getContent());
        return "approveContent/contentToApprovePosts";
    }
    @GetMapping("contentToApproveBlogs")
    public String contentToApproveBlogs( @RequestParam(required = false, defaultValue = "1") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @RequestParam(required = false) String query,
                                         @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                         @RequestParam(required = false, defaultValue = "asc") String sortDirection,
                                          Model model) {
        model.addAttribute("blogs", blogService.getBlogsByStatus(status,query, page, size, sortBy, sortDirection));
        return "admin/contentToApproveBlogs";
    }
    @GetMapping("contentToApproveEvents")
    public String contentToApproveEvents( @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size,
                                          @RequestParam(required = false) String query,
                                          @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                          @RequestParam(required = false, defaultValue = "asc") String sortDirection,
                                          Model model) {
        model.addAttribute("blogs", eventService.getEventsByStatus(status,query, page, size, sortBy, sortDirection));
        return "redirect:/admin";
    }
    @PostMapping("eventApprove/{id}")
    public String eventApprove(
            @PathVariable Long id,
            Principal principal
    ){
        eventService.approve(id, principal.getName());
        return "redirect:/admin";
    }
    @GetMapping("postApprove/{id}")
    public String postApprove(
            @PathVariable Long id,
            Principal principal
    ){
        postService.approve(id, principal.getName());
        return "redirect:/admin";
    }
    @PostMapping("blogApprove/{id}")
    public String blogApprove(
            @PathVariable Long id,
            Principal principal
    ){
        blogService.approve(id, principal.getName());
        return "redirect:/admin";
    }



}

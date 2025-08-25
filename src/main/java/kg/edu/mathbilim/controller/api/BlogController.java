package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@Slf4j
@RestController("restBlogs")
@RequestMapping("api/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<Page<BlogDto>> getBlogs(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size,
                                                  @RequestParam(required = false) String query,
                                                  @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                  @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ofNullable(blogService.getPage(query, page, size, sortBy, sortDirection));
    }

    @GetMapping("{id}")
    public ResponseEntity<BlogDto> getBlog(@PathVariable Long id) {
        return ResponseEntity.ofNullable(blogService.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/share")
    public ResponseEntity<Void> shareBlog(@PathVariable Long id) {
        blogService.incrementShareCount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("main")
    public ResponseEntity<?> getLatestBlog() {
        return ResponseEntity.ofNullable(blogService.getBlogsByMainPage());
    }



    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN') or @blogSecurity.isOwner(#id,  principal.username)")
    @PostMapping("delete/{id}")
    public String deleteBlog(@PathVariable Long id, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("delete blog {}", id);
        log.info("User trying to delete blog: {}",
                principal != null ? principal.getName() : "anonymous");
        blogService.delete(id);
        return "redirect:/blog";
    }
}

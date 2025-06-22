package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

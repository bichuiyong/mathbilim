package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("restPost")
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("users/{userId}")
    public ResponseEntity<Page<PostDto>> getUserPosts(@PathVariable Long userId,
                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "10") int size,
                                                      @RequestParam(required = false) String query,
                                                      @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                      @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ofNullable(postService.getUserPosts(userId, query, page, size, sortBy, sortDirection));
    }

    @GetMapping
    public ResponseEntity<Page<PostDto>> getPosts(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size,
                                                  @RequestParam(required = false) String query,
                                                  @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                  @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ofNullable(postService.getPage(query, page, size, sortBy, sortDirection));
    }

    @GetMapping("by-status")
    public ResponseEntity<Page<PostDto>> getPostsByStatus(@RequestParam String status,
                                                          @RequestParam(required = false, defaultValue = "1") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int size,
                                                          @RequestParam(required = false) String query,
                                                          @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                          @RequestParam(required = false, defaultValue = "asc") String sortDirection,
                                                          @CookieValue(value = "lang", defaultValue = "ru", required = false) String lang) {
        return ResponseEntity.ofNullable(postService.getPostsByStatus(status, query, page, size, sortBy, sortDirection, lang));
    }


    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ofNullable(postService.getById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> togglePost(@PathVariable Long id) {
        postService.togglePostApproving(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("main")
    public ResponseEntity<?> getLatestPost() {
        return ResponseEntity.ofNullable(postService.getPostByMainPage());
    }

    @PostMapping("{id}/share")
    public ResponseEntity<Void> sharePost(@PathVariable Long id) {
        postService.incrementShareCount(id);
        return ResponseEntity.ok().build();
    }
}

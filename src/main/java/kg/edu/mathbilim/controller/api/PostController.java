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

    @GetMapping
    public ResponseEntity<Page<PostDto>> getFiles(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size,
                                                  @RequestParam(required = false) String query,
                                                  @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                  @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ofNullable(postService.getPostPage(query, page, size, sortBy, sortDirection));
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> getFile(@PathVariable Long id) {
        return ResponseEntity.ofNullable(postService.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }
}

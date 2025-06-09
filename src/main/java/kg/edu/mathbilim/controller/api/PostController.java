package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.PostDto;
import kg.edu.mathbilim.enums.PostType;
import kg.edu.mathbilim.service.interfaces.PostService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("restPost")
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/{type}")
    public ResponseEntity<Page<PostDto>> getPosts(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size,
                                                  @RequestParam(required = false) String query,
                                                  @RequestParam(required = false, defaultValue = "title") String sortBy,
                                                  @RequestParam(required = false, defaultValue = "asc") String sortDirection,
                                                  @PathVariable String type) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        return ResponseEntity.ofNullable(postService.getPostsByType(type, query, pageable));
    }

    @GetMapping("/{type}/{id}")
    public ResponseEntity<PostDto> getFile(@PathVariable String type, @PathVariable Long id) {
        return ResponseEntity.ofNullable(postService.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }

}

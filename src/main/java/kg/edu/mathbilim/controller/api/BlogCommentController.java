package kg.edu.mathbilim.controller.api;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.blog.BlogCommentDto;
import kg.edu.mathbilim.service.interfaces.blog.BlogCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("blogComments")
@RequiredArgsConstructor
public class BlogCommentController {
    private final BlogCommentService blogCommentService;



    @GetMapping("{blogId}")
    public ResponseEntity<List<BlogCommentDto>> getBlogComment(@PathVariable Long blogId) {
        return ResponseEntity.ok(blogCommentService.getBlogCommentByBlogId(blogId));
    }

    @DeleteMapping("{blogCommentId}")
    public ResponseEntity<?> deleteBlogComment(@PathVariable Long blogCommentId) {
        blogCommentService.deleteBlogComment(blogCommentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{blogCommentId}")
    public ResponseEntity<?> updateBlogComment(@PathVariable Long blogCommentId, @RequestBody @Valid BlogCommentDto blogCommentDto) {
        blogCommentDto.setId(blogCommentId);
        blogCommentService.updateBlogComment(blogCommentDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{blogId}")
    public ResponseEntity<?> createBlogComment(@PathVariable Long blogId, @RequestBody @Valid BlogCommentDto blogCommentDto) {
        blogCommentDto.setBlogId(blogId);
        blogCommentService.createBlogComment(blogCommentDto);
        return ResponseEntity.ok().build();
    }

}

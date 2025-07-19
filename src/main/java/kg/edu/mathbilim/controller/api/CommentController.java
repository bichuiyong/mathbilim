package kg.edu.mathbilim.controller.api;


import kg.edu.mathbilim.dto.CommentCreateDto;
import kg.edu.mathbilim.dto.CommentDto;
import kg.edu.mathbilim.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    public List<CommentDto> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsForPost(postId);
    }

    @PostMapping("/post/new/{postId}")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentCreateDto commentDto, @PathVariable Long postId) {
        System.out.println("Получен комментарий: " + commentDto.getContent());
        System.out.println("Получен id: " + postId);
        CommentDto savedComment = commentService.addCommentPost(commentDto, postId);
        return ResponseEntity.ok(savedComment);
    }

    @PostMapping("/delete/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/blog/{blogId}")
    public List<CommentDto> getCommentsByBlog(@PathVariable Long blogId) {
        return commentService.getCommentsForBlog(blogId);
    }


    @PostMapping("/blog/new/{blogId}")
    public ResponseEntity<CommentDto> addCommentBlog(@RequestBody CommentCreateDto commentDto, @PathVariable Long blogId) {
        CommentDto savedComment = commentService.addCommentBlog(commentDto, blogId);
        return ResponseEntity.ok(savedComment);
    }

    @GetMapping("/news/{newsId}")
    public List<CommentDto> getCommentsByNews(@PathVariable Long newsId) {
        return commentService.getCommentsForNews(newsId);
    }

    @PostMapping("/news/new/{newsId}")
    public ResponseEntity<CommentDto> addCommentNews(@RequestBody CommentCreateDto commentDto, @PathVariable Long newsId) {
        CommentDto savedComment = commentService.addCommentNews(commentDto, newsId);
        return ResponseEntity.ok(savedComment);
    }
}

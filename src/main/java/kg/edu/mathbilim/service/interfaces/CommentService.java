package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.CommentCreateDto;
import kg.edu.mathbilim.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto addCommentPost(CommentCreateDto comment, Long postId);

    CommentDto addCommentBlog(CommentCreateDto comment, Long postId);

    CommentDto addCommentNews(CommentCreateDto comment, Long postId);

    void deleteComment(Long commentId);

    List<CommentDto> getCommentsForPost(Long postId);

    List<CommentDto> getCommentsForBlog(Long blogId);

    List<CommentDto> getCommentsForNews(Long newsId);
}

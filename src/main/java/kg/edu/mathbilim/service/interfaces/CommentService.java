package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.model.Comment;

public interface CommentService {
    void deleteComment(Long commentId);

    void updateComment(Comment comment);

    Comment saveComment(Comment comment);
}

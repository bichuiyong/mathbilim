package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.model.Comment;
import kg.edu.mathbilim.repository.CommentRepository;
import kg.edu.mathbilim.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void updateComment(Comment comment) {
        Comment commentForUpdate = commentRepository.findById(comment.getId()).orElse(null);
        if (commentForUpdate != null) {
            commentForUpdate.setContent(comment.getContent());
            commentRepository.save(commentForUpdate);
        }
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}

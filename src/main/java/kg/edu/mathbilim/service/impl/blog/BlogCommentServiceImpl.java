package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.blog.BlogCommentDto;
import kg.edu.mathbilim.exception.nsee.BlogCommentNotFoundException;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.exception.nsee.CommentNotFoundException;
import kg.edu.mathbilim.exception.nsee.UnauthorizedAccessException;
import kg.edu.mathbilim.mapper.blog.BlogCommentMapper;
import kg.edu.mathbilim.model.Comment;
import kg.edu.mathbilim.model.blog.BlogComment;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.blog.BlogCommentRepository;
import kg.edu.mathbilim.service.interfaces.CommentService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.blog.BlogCommentService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogCommentServiceImpl implements BlogCommentService {
    private final BlogCommentRepository blogCommentRepository;
    private final BlogCommentMapper blogCommentMapper;
    private final BlogService blogService;
    private final CommentService commentService;
    private final UserService userService;

    @Override
    public List<BlogCommentDto> getBlogCommentByBlogId(Long blogId) {
        return blogCommentRepository.findAllByBlog_Id(blogId).stream()
                .map(blogCommentMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void createBlogComment(BlogCommentDto blogCommentDto) {
        if (blogService.existsBlogById(blogCommentDto.getBlogId())) {
            User commentAuthor = userService.getAuthUserEntity();
            Comment comment = commentService.saveComment(
                    Comment.builder()
                            .author(commentAuthor)
                            .content(blogCommentDto.getContent())
                            .build()
            );
            BlogComment blogComment = blogCommentMapper.toEntity(blogCommentDto);
            blogComment.setComment(comment);
            blogCommentRepository.save(blogComment);
            log.info("Created blog comment for blog {}, user {}", blogCommentDto.getBlogId(), commentAuthor.getEmail());
        } else {
            throw new BlogNotFoundException();
        }

    }
    @Transactional
    @Override
    public void deleteBlogComment(Long blogCommentId) {
        BlogComment blogComment = findBlogCommentById(blogCommentId);
        if (blogComment != null) {
            validateOwnership(blogComment);
            commentService.deleteComment(blogComment.getComment().getId());
            log.info("Deleting blog comment {}", blogCommentId);
        } else {
            throw new BlogCommentNotFoundException();
        }
    }
    @Transactional
    @Override
    public void updateBlogComment(BlogCommentDto blogCommentDto) {
        BlogComment blogComment = findBlogCommentById(blogCommentDto.getBlogId());
        if (blogComment != null) {
            validateOwnership(blogComment);
            Comment comment = blogComment.getComment();
            comment.setContent(blogCommentDto.getContent());
            commentService.updateComment(comment);
            log.info("Updated blog comment {}", blogComment);
        } else {
            throw new BlogCommentNotFoundException();
        }
    }

    @Override
    public BlogComment findBlogCommentById(Long blogCommentId) {
        return blogCommentRepository.findById(blogCommentId).orElseThrow(BlogCommentNotFoundException::new);
    }


    private void validateOwnership(BlogComment blogComment) {
        User currentUser = userService.getAuthUserEntity();
        if (!currentUser.getEmail().equals(blogComment.getComment().getAuthor().getEmail())) {
            throw new UnauthorizedAccessException("User is not authorized to modify this comment");
        }
    }

}

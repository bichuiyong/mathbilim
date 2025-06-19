package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.blog.BlogCommentDto;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.exception.nsee.CommentNotFoundException;
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
        } else {
            throw new BlogNotFoundException();
        }

    }
    @Override
    public void deleteBlogComment(Long blogCommentId) {
        BlogComment blogComment = blogCommentRepository.findById(blogCommentId).orElse(null);
        User commentAuthor = userService.getAuthUserEntity();
        if (blogComment != null && commentAuthor.getEmail().equals(blogComment.getComment().getAuthor().getEmail())) {
            commentService.deleteComment(blogComment.getComment().getId());
        } else {
            throw new CommentNotFoundException();
        }
    }

    @Override
    public void updateBlogComment(BlogCommentDto blogCommentDto) {
        BlogComment blogComment = blogCommentRepository.findById(blogCommentDto.getId()).orElse(null);
        User commentAuthor = userService.getAuthUserEntity();
        if (blogComment != null && commentAuthor.getEmail().equals(blogComment.getComment().getAuthor().getEmail())) {
            Comment comment = blogComment.getComment();
            comment.setContent(blogCommentDto.getContent());
            commentService.updateComment(comment);
        } else {
            throw new CommentNotFoundException();
        }
    }
}

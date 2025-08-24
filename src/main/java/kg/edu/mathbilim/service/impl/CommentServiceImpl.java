package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.CommentCreateDto;
import kg.edu.mathbilim.dto.CommentDto;
import kg.edu.mathbilim.mapper.CommentMapper;
import kg.edu.mathbilim.model.Comment;
import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.repository.CommentRepository;
import kg.edu.mathbilim.service.interfaces.CommentService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostService postService;
    private final BlogService blogService;
    private final UserService userService;

    public CommentDto getComment(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto)
                .orElse(null);
    }

    @Override
    public CommentDto addCommentPost(CommentCreateDto comment, Long postId) {
        Post post = postService.findByPostId(postId);
        return createComment(comment, com -> com.getPosts().add(post));
    }

    @Override
    public CommentDto addCommentBlog(CommentCreateDto comment, Long blogId) {
        Blog blog = blogService.findByBlogId(blogId);
        return createComment(comment, com -> com.getBlogs().add(blog));
    }


    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentsForPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostsId(postId);
        log.info("getCommentsForPost: {}", comments.size());
        return mapCommentsToDto(comments);
    }

    @Override
    public List<CommentDto> getCommentsForBlog(Long blogId) {
        return mapCommentsToDto(commentRepository.findByBlogsId(blogId));
    }


    private CommentDto createComment(CommentCreateDto commentDto, Consumer<Comment> entitySetter) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setAuthor(userService.getAuthUserEntity());

        entitySetter.accept(comment);

        commentRepository.saveAndFlush(comment);
        return commentMapper.toDto(comment);
    }

    private List<CommentDto> mapCommentsToDto(List<Comment> comments) {
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
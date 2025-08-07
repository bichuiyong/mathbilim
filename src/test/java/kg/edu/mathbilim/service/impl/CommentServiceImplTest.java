package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.CommentCreateDto;
import kg.edu.mathbilim.dto.CommentDto;
import kg.edu.mathbilim.mapper.CommentMapper;
import kg.edu.mathbilim.model.Comment;
import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.CommentRepository;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private PostService postService;
    @Mock
    private BlogService blogService;
    @Mock
    private NewsService newsService;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getComment_shouldReturnDto() {
        Comment comment = new Comment();
        comment.setId(1L);
        CommentDto dto = new CommentDto();
        dto.setId(1L);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentMapper.toDto(comment)).thenReturn(dto);

        CommentDto result = commentService.getComment(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void addCommentPost_shouldAddComment() {
        CommentCreateDto createDto = new CommentCreateDto();
        createDto.setContent("test content");

        Post post = new Post();
        when(postService.findByPostId(1L)).thenReturn(post);
        when(userService.getAuthUserEntity()).thenReturn(new User());

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        Comment saved = new Comment();
        CommentDto expectedDto = new CommentDto();
        expectedDto.setContent("test content");

        when(commentRepository.saveAndFlush(any())).thenReturn(saved);
        when(commentMapper.toDto(any())).thenReturn(expectedDto);

        CommentDto result = commentService.addCommentPost(createDto, 1L);

        verify(commentRepository).saveAndFlush(captor.capture());
        assertEquals("test content", captor.getValue().getContent());
        assertEquals("test content", result.getContent());
    }

    @Test
    void addCommentBlog_shouldAddComment() {
        CommentCreateDto dto = new CommentCreateDto();
        dto.setContent("blog comment");
        Blog blog = new Blog();
        when(blogService.findByBlogId(1L)).thenReturn(blog);
        when(userService.getAuthUserEntity()).thenReturn(new User());

        when(commentMapper.toDto(any())).thenReturn(new CommentDto());

        CommentDto result = commentService.addCommentBlog(dto, 1L);

        assertNotNull(result);
        verify(commentRepository).saveAndFlush(any());
    }

    @Test
    void addCommentNews_shouldAddComment() {
        CommentCreateDto dto = new CommentCreateDto();
        dto.setContent("news comment");
        News news = new News();
        when(newsService.findByNewsId(1L)).thenReturn(news);
        when(userService.getAuthUserEntity()).thenReturn(new User());

        when(commentMapper.toDto(any())).thenReturn(new CommentDto());

        CommentDto result = commentService.addCommentNews(dto, 1L);

        assertNotNull(result);
        verify(commentRepository).saveAndFlush(any());
    }

    @Test
    void deleteComment_shouldCallRepository() {
        commentService.deleteComment(1L);
        verify(commentRepository).deleteById(1L);
    }

    @Test
    void getCommentsForPost_shouldReturnDtos() {
        Comment comment = new Comment();
        CommentDto dto = new CommentDto();

        when(commentRepository.findByPostsId(1L)).thenReturn(Collections.singletonList(comment));
        when(commentMapper.toDto(comment)).thenReturn(dto);

        var result = commentService.getCommentsForPost(1L);
        assertEquals(1, result.size());
    }

    @Test
    void getCommentsForBlog_shouldReturnDtos() {
        Comment comment = new Comment();
        CommentDto dto = new CommentDto();

        when(commentRepository.findByBlogsId(1L)).thenReturn(Collections.singletonList(comment));
        when(commentMapper.toDto(comment)).thenReturn(dto);

        var result = commentService.getCommentsForBlog(1L);
        assertEquals(1, result.size());
    }

    @Test
    void getCommentsForNews_shouldReturnDtos() {
        Comment comment = new Comment();
        CommentDto dto = new CommentDto();

        when(commentRepository.findByNewsId(1L)).thenReturn(Collections.singletonList(comment));
        when(commentMapper.toDto(comment)).thenReturn(dto);

        var result = commentService.getCommentsForNews(1L);
        assertEquals(1, result.size());
    }
}

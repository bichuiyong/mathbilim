package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.abstracts.DisplayContentDto;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.mapper.blog.BlogMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.blog.BlogRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.blog.BlogTranslationService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceImplTest {

    @Mock
    BlogMapper blogMapper;
    @Mock
    BlogRepository blogRepository;

    @Mock
    UserService userService;

    @Mock
    BlogTranslationService blogTranslationService;

    @Mock
    FileService fileService;

    @Mock
    UserNotificationService userNotificationService;

    @InjectMocks
    BlogServiceImpl blogService;


    @Test
    void create() {
        UserDto creatorDto = UserDto.builder()
                .id(1L)
                .name("Test")
                .build();
        User creator = User.builder()
                .id(1L)
                .name("Test")
                .build();
        Blog blog = Blog.builder()
                .id(1L)
                .creator(creator)
                .build();
        BlogDto blogDto = BlogDto.builder()
                .id(1L)
                .build();
        BlogDto expectedDto = BlogDto.builder()
                .id(1L)
                .creator(creatorDto)
                .build();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "blog-test.jpg",
                "image/jpeg",
                "test".getBytes());
        when(userService.getAuthUser()).thenReturn(creatorDto);
        when(blogMapper.toEntity(any(BlogDto.class))).thenReturn(blog);
        when(blogMapper.toDto(any(Blog.class))).thenReturn(blogDto);
        when(fileService.uploadFileReturnEntity(any(MultipartFile.class),anyString())).thenReturn(new File());
        when(blogRepository.save(blog)).thenReturn(blog);
        doNothing().when(blogTranslationService).saveTranslations(anyLong(), anySet());


        BlogDto result = blogService.create(blogDto, file);



        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getCreator().getName(), result.getCreator().getName());
        verify(blogRepository, times(1)).save(any(Blog.class));
        verify(fileService, times(1)).uploadFileReturnEntity(any(), any());
    }

    @Test
    void incrementViewCount() {
        Long blogId = 1L;

        blogService.incrementViewCount(blogId);

        verify(blogRepository, times(1)).incrementViewCount(blogId);
    }

    @Test
    void incrementShareCount() {
        Long blogId = 1L;

        blogService.incrementShareCount(blogId);

        verify(blogRepository, times(1)).incrementShareCount(blogId);
    }

//    @Test
//    void getDisplayBlogById() {
//        Long blogId = 1L;
//        String language = "ru";
//        DisplayContentDto expectedDto = new DisplayContentDto(
//                blogId,
//                2L,
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                100L,
//                5L,
//                10L,
//                3L,
//                ContentStatus.APPROVED,
//                "Тестовый заголовок",
//                "Контент"
//        );
//
//        when(blogRepository.findDisplayBlogById(1L, language)).thenReturn(Optional.of(expectedDto));
//
//        DisplayContentDto displayContentDto = blogService.getDisplayBlogById(blogId);
//
//
//        assertNotNull(displayContentDto);
//        assertEquals(expectedDto.getId(), displayContentDto.getId());
//        verify(blogRepository, times(1)).findDisplayBlogById(1L, language);
//    }

    @Test
    void getDisplayBlogById_ShouldThrowException_whenBlogNotFound() {
        Long blogId = 999L;
        String language = "ru";

        when(blogRepository.findDisplayBlogById(blogId, language)).thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class,
                () -> blogService.getDisplayBlogById(blogId)
        );

        verify(blogRepository, times(1)).findDisplayBlogById(blogId, language);
    }

    @Test
    void getAllDisplayBlogs() {

        int page = 1;
        int size = 10;
        String sortBy = "createdAt";
        String sortDirection = "desc";
        String lang = "ru";

        Pageable expectedPageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));

        List<DisplayContentDto> dtoList = List.of(
                new DisplayContentDto(
                        1L,
                        2L,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        100L,
                        5L,
                        10L,
                        3L,
                        ContentStatus.APPROVED,
                        "Заголовок",
                        "Контент")
        );
        Page<DisplayContentDto> expectedPage = new PageImpl<>(dtoList, expectedPageable, dtoList.size());

        when(blogRepository.findAllDisplayBlogsByLanguage(eq(lang), any(Pageable.class)))
                .thenReturn(expectedPage);

        Page<BlogDto> result = blogService.getAllDisplayBlogs(page, size, sortBy, sortDirection);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(blogRepository).findAllDisplayBlogsByLanguage(eq(lang), any(Pageable.class));
    }

    @Test
    void getRelatedBlogs_shouldReturnListOfRelatedBlogs() {
        Long excludeId = 1L;
        int limit = 3;
        String lang = "ru";
        Pageable pageable = PageRequest.of(0, limit);

        List<DisplayContentDto> expectedList = List.of(
                new DisplayContentDto(2L,
                        3L,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        10L,
                        2L,
                        5L,
                        1L,
                        ContentStatus.APPROVED,
                        "title",
                        "content")
        );

        when(blogRepository.findRelatedBlogs(eq(excludeId), eq(lang), eq(pageable)))
                .thenReturn(expectedList);


        List<DisplayContentDto> result = blogService.getRelatedBlogs(excludeId, limit);


        assertEquals(expectedList, result);
        verify(blogRepository).findRelatedBlogs(eq(excludeId), eq(lang), eq(pageable));
    }

    @Test
    void getBlogsByStatus_shouldCallFindBlogsByStatus_whenQueryIsEmpty() {
        String status = "APPROVED";
        String query = "";
        int page = 1;
        int size = 5;
        String sortBy = "createdAt";
        String sortDirection = "desc";

        Page<Blog> blogPage = new PageImpl<>(List.of(new Blog()));
        when(blogRepository.findBlogsByStatus(eq(ContentStatus.APPROVED), any(Pageable.class)))
                .thenReturn(blogPage);
        when(blogMapper.toDto(any(Blog.class))).thenReturn(new BlogDto());

        Page<BlogDto> result = blogService.getBlogsByStatus(status, query, page, size, sortBy, sortDirection);

        assertNotNull(result);
        verify(blogRepository).findBlogsByStatus(eq(ContentStatus.APPROVED), any(Pageable.class));
    }

    @Test
    void getBlogsByStatus_shouldCallGetBlogsByStatusWithQuery_whenQueryIsNotEmpty() {
        String status = "APPROVED";
        String query = "math";
        int page = 1;
        int size = 5;
        String sortBy = "createdAt";
        String sortDirection = "desc";

        Page<Blog> blogPage = new PageImpl<>(List.of(new Blog()));
        when(blogRepository.getBlogsByStatusWithQuery(eq(ContentStatus.APPROVED), eq(query), any(Pageable.class)))
                .thenReturn(blogPage);
        when(blogMapper.toDto(any(Blog.class))).thenReturn(new BlogDto());

        Page<BlogDto> result = blogService.getBlogsByStatus(status, query, page, size, sortBy, sortDirection);


        assertNotNull(result);
        verify(blogRepository).getBlogsByStatusWithQuery(eq(ContentStatus.APPROVED), eq(query), any(Pageable.class));
    }

    @Test
    void approve() {
        Long blogId = 5L;

        doNothing().when(userNotificationService).notifyAllSubscribed(NotificationEnum.BLOG, "New blog");

        when(blogRepository.findById(blogId)).thenReturn(Optional.of(new Blog()));

        assertDoesNotThrow(() -> blogService.approve(blogId, anyString()));
        verify(blogRepository, times(1)).findById(blogId);
        verify(blogRepository, times(1)).save(any(Blog.class));
        verify(userNotificationService, times(1)).notifyAllSubscribed(NotificationEnum.BLOG, "New blog");

    }

    @Test
    void approve_shouldThrowException_whenBlogNotFound() {
        Long blogId = 5L;

        when(blogRepository.findById(blogId)).thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class, () -> blogService.approve(blogId, anyString()));
        verify(blogRepository, times(1)).findById(blogId);
        verify(blogRepository, times(0)).save(any(Blog.class));
        verify(userNotificationService, times(0)).notifyAllSubscribed(NotificationEnum.BLOG, "New blog");

    }

    @Test
    void findByBlogId() {
        Long blogId = 5L;
        Blog blog = Blog.builder().id(blogId).build();

        when(blogRepository.findById(blogId)).thenReturn(Optional.of(blog));

        Blog response = blogService.findByBlogId(blogId);

        assertNotNull(response);
        assertEquals(blogId, response.getId());
        verify(blogRepository, times(1)).findById(blogId);



    }

    @Test
    void findByBlogIdShouldThrowException_whenBlogNotFound() {
        Long blogId = 5L;

        when(blogRepository.findById(blogId)).thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class, () -> blogService.findByBlogId(blogId));

        verify(blogRepository, times(1)).findById(blogId);

    }



}

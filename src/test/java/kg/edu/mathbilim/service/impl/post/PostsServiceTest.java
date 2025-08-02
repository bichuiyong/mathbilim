package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.PostNotFoundException;
import kg.edu.mathbilim.mapper.post.PostMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.post.PostRepository;
import kg.edu.mathbilim.service.impl.notification.UserNotificationServiceImpl;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.util.PaginationUtil;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostsServiceTest {
    @Mock
    private PostMapper postMapper;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserService userService;
    @Mock
    private FileService fileService;
    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private MultipartFile attachment;
    @Mock
    private MultipartFile image;
    @Mock
    private UserNotificationServiceImpl notificationService;
    @Mock
    private PostTranslationServiceImpl translationService;




    private Post post;
    private PostDto postDto;
    private User user;
    private UserDto userDto;
    private CreatePostDto createPostDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .email("test@test.com")
                .build();
        user = User.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .email("test@test.com")
                .build();

        post = new Post();
        post.setId(1L);
        post.setCreator(user);
        post.setApprovedBy(user);

        postDto = PostDto.builder()
                .id(post.getId())
                .creator(userDto)
                .approvedBy(userDto)
                .postFiles(List.of(FileDto.builder()
                        .s3Link("posts/s3")
                        .filename("kddks")
                        .build()))
                .mainImage(FileDto.builder().build())
                .postTranslations(List.of(PostTranslationDto.builder().postId(1L).build()))
                .build();

        createPostDto = CreatePostDto.builder()
                .post(postDto)
                .attachments(Arrays.array(attachment))
                .image(image)
                .build();


        lenient().when(userService.getAuthUser()).thenReturn(userDto);
        lenient().when(postMapper.toDto(any(Post.class))).thenReturn(postDto);
        lenient().when(postRepository.save(any(Post.class))).thenReturn(post);
    }
    @Test
    void correctPostCreationTest() {
        when(image.isEmpty()).thenReturn(false);
        when(postMapper.toEntity(any(PostDto.class))).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(fileService.uploadFilesForPost(any(), any()))
                .thenReturn(List.of(File.builder().id(1L).build()));
        when(fileService.uploadFileReturnEntity(any(), any()))
                .thenReturn((File.builder().id(1L).build()));
        when(postMapper.toDto(any(Post.class))).thenReturn(postDto);
        doNothing().when(translationService)
                .saveTranslations(any(), any());

        PostDto result = postService.createPost(createPostDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(postRepository, times(1)).save(any(Post.class));
        verify(fileService, times(1)).uploadFileReturnEntity(any(), any());
        verify(fileService, times(1)).uploadFilesForPost(any(), any());

    }

    @Test
    void findPostByIdTest(){
        when(postRepository.findById(post.getId())).thenReturn(Optional.ofNullable(post));
        Post post = postService.findByPostId(postDto.getId());
        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(postDto.getId());
    }

    @Test
    void approvePostTest(){
        when(postRepository.findById(post.getId())).thenReturn(Optional.ofNullable(post));
        doNothing().when(notificationService)
                .notifyAllSubscribed(any(NotificationEnum.class), any(String.class));

        postService.approve(postDto.getId());
        Post post = postService.findByPostId(postDto.getId());
        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(postDto.getId());
        assertThat(post.getStatus()).isEqualTo(ContentStatus.APPROVED);
        verify(notificationService, times(1)).notifyAllSubscribed(any(), any());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void getPostsByStatusTest(){
        Pageable pageable = PaginationUtil.createPageableWithSort(1, 10, "name", "asc");
        when(postRepository.getPostsByStatus(eq(ContentStatus.APPROVED), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(post)));
        when(postMapper.toDto(any(Post.class))).thenReturn(postDto);
        Page<PostDto> userPosts =  postService.getPostsByStatus("APPROVED", null, 1, 10, "name", "asc","en");
        assertThat(userPosts).isNotNull();
        Assertions.assertThat(userPosts.getContent()).hasSize(1);
        verify(postRepository, times(1)).getPostsByStatus(eq(ContentStatus.APPROVED), eq(pageable));
    }

    @Test
    void getUserPostsTest(){
        Pageable pageable = PaginationUtil.createPageableWithSort(1, 10, "name", "asc");
        String query = "j";
        when(postRepository.getPostByCreator_Id(eq(user.getId()),  eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(post)));
        when(postRepository.getUserPostsWithQuery(eq(user.getId()), any(),  eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(post)));
        when(postMapper.toDto(any(Post.class))).thenReturn(postDto);
        Page<PostDto> userPosts =  postService.getUserPosts(user.getId(), null, 1, 10, "name", "asc");
        Page<PostDto> userPostsWithQuery = postService.getUserPosts(user.getId(),query , 1, 10, "name", "asc");
        assertThat(userPosts).isNotNull();
        assertThat(userPostsWithQuery).isNotNull();
        Assertions.assertThat(userPosts.getContent()).hasSize(1);
        verify(postRepository, times(1)).getPostByCreator_Id(eq(user.getId()), eq(pageable));
        verify(postRepository, times(1)).getUserPostsWithQuery(eq(user.getId()),any(), eq(pageable));
    }
    @Test
    void getPostById_shouldThrowException_whenNotFound() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> postService.getPostById(1L))
                .isInstanceOf(PostNotFoundException.class);
    }



}

package kg.edu.mathbilim.posts;

import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.mapper.post.PostMapper;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.post.PostRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

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
    private PostService postService;
    @Mock
    private MultipartFile attachment;
    @Mock
    private MultipartFile image;



    private Post post;
    private PostDto postDto;
    private User user;
    private UserDto userDto;


    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setCreator(User.builder().id(1L).build());
        post.setApprovedBy(User.builder().id(1L).build());

        postDto = new PostDto().builder()
                .id(post.getId())
                .creator(userDto)
                .approvedBy(UserDto.builder().id(2L).build())
                .build();
        lenient().when(userService.getAuthUser()).thenReturn(userDto);
        lenient().when(postMapper.toDto(any(Post.class))).thenReturn(postDto);
    }
    @Test
    void correctPostCreationTest(){
        CreatePostDto postDto = new CreatePostDto();
        postDto.setAttachments(attachment);
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when()
        Post p = postService.createPost()
    }
}

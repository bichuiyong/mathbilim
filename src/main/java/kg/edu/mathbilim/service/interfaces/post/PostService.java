package kg.edu.mathbilim.service.interfaces.post;

import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import org.springframework.data.domain.Page;

public interface PostService {
    PostDto getById(Long id);

    Page<PostDto> getPostPage(String query, int page, int size, String sortBy, String sortDirection);

    void delete(Long id);

    PostDto createPost(CreatePostDto postDto);

    Page<PostDto> getUserPosts(Long userId, String query, int page, int size, String sortBy, String sortDirection);


    void togglePostApproving(Long id);

    Page<PostDto> getPostsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection);
}

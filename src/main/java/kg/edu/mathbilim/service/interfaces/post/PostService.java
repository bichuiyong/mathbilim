package kg.edu.mathbilim.service.interfaces.post;

import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService extends BaseTranslatableService<PostDto, PostTranslationDto> {
    PostDto createPost(CreatePostDto postDto);

    Page<PostDto> getUserPosts(Long userId, String query, int page, int size, String sortBy, String sortDirection);

    void togglePostApproving(Long id);

//    Page<PostDto> getPostsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection);

    void approve(Long id);
    Page<PostDto> getPostsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection, String lang);

    PostDto getPostById(Long id);
}

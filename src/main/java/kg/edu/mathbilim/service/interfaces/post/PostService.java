package kg.edu.mathbilim.service.interfaces.post;

import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService extends BaseTranslatableService<PostDto, PostTranslationDto> {
    PostDto createPost(CreatePostDto postDto);

    Page<PostDto> getUserPosts(Long userId, String query, int page, int size, String sortBy, String sortDirection);

    void togglePostApproving(Long id);

    Page<PostDto> getPostsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection, String lang);

    PostDto getPostById(Long id);

    Page<PostDto> getPostsByCreator(Long creatorId, Pageable pageable);

    Page<PostDto> getPostsForModeration(Pageable pageable);

    Post findByPostId(Long id);
}

package kg.edu.mathbilim.service.interfaces.post;

import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService extends BaseTranslatableService<PostDto, PostTranslationDto> {
    PostDto createPost(CreatePostDto postDto);

    Page<PostDto> getUserPosts(Long userId, String query, int page, int size, String sortBy, String sortDirection);

    Long countPostsForModeration();

    void togglePostApproving(Long id);

//    Page<PostDto> getPostsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection);

    void approve(Long id, String email);
    Page<PostDto> getPostsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection, String lang);

    void reject(Long id, String email);

    PostDto getPostById(Long id);

    Page<PostDto> getPostsByCreator(Long creatorId, Pageable pageable, String query);

    Page<PostDto> getHisotryPost(Long creatorId, Pageable pageable, String query, String status);

    Page<PostDto> getPostsForModeration(Pageable pageable, String query);

    Page<PostDto> getAllPostByStatus(String status, String query, int page, int size, String sortBy, String sortDirection);

    Post findByPostId(Long id);

    List<PostDto> getPostByMainPage();
}

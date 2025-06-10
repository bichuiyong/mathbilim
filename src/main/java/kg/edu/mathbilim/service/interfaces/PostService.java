package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PostDto getById(Long id);

    Page<PostDto> getPostPage(String query, int page, int size, String sortBy, String sortDirection);

    void delete(Long id);

    PostDto createPost(PostDto postDto, MultipartFile[] files);
}

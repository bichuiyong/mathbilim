package kg.edu.mathbilim.service.interfaces;
import kg.edu.mathbilim.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PostDto getById(Long id);

    Page<PostDto> getPostsByType(String PostType, String query, Pageable pageable);

    void delete(Long id);

    PostDto createPost(PostDto postDto, MultipartFile[] files);
}

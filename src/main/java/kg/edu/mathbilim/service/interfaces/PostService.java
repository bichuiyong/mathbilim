package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.PostDto;
import kg.edu.mathbilim.enums.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostDto getById(Long id);

    Page<PostDto> getPostsByType(String PostType, String query, Pageable pageable);

    void delete(Long id);
}

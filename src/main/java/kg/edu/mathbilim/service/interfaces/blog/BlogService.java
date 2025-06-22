package kg.edu.mathbilim.service.interfaces.blog;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.dto.blog.DisplayBlogDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService extends BaseTranslatableService<BlogDto, BlogTranslationDto> {
    BlogDto create(@Valid BlogDto blogDto, MultipartFile mainImage);

    DisplayBlogDto getDisplayBlogById(Long id);

    Page<DisplayBlogDto> getAllDisplayBlogs(int page, int size, String sortBy, String sortDirection);

    List<DisplayBlogDto> getRelatedBlogs(Long excludeId, int limit);

    @Transactional
    void incrementViewCount(Long id);

    @Transactional
    void incrementShareCount(Long id);
}

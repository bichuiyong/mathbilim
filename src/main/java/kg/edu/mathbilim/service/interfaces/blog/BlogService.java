package kg.edu.mathbilim.service.interfaces.blog;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.abstracts.DisplayContentDto;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService extends BaseTranslatableService<BlogDto, BlogTranslationDto> {
    BlogDto create(@Valid BlogDto blogDto, MultipartFile mainImage);

    DisplayContentDto getDisplayBlogById(Long id);

    Page<DisplayContentDto> getAllDisplayBlogs(int page, int size, String sortBy, String sortDirection);

    List<DisplayContentDto> getRelatedBlogs(Long excludeId, int limit);

    @Transactional
    void incrementViewCount(Long id);

    @Transactional
    void incrementShareCount(Long id);
}

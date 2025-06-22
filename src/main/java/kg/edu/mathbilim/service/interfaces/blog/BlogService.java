package kg.edu.mathbilim.service.interfaces.blog;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.dto.blog.DisplayBlogDto;
import kg.edu.mathbilim.model.blog.Blog;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService {
    Blog findBlogById(Long id);

    Boolean existsBlogById(Long id);

    Page<BlogDto> getBlogPage(String query, int page, int size, String sortBy, String sortDirection);

    BlogDto getById(Long id);

    void deleteById(Long id);

    @Transactional
    void setBlogTranslations(List<BlogTranslationDto> translations, Long blogId);

    BlogDto createBlog(@Valid BlogDto blogDto, MultipartFile mainImage);

    DisplayBlogDto getDisplayBlogById(Long id, String languageCode);

    Page<DisplayBlogDto> getAllDisplayBlogs(int page, int size, String sortBy, String sortDirection, String languageCode);

    List<DisplayBlogDto> getRelatedBlogs(Long excludeId, String languageCode, int limit);

    @Transactional
    void incrementViewCount(Long id);

    @Transactional
    void incrementShareCount(Long id);
}

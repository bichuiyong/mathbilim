package kg.edu.mathbilim.service.interfaces.blog;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.abstracts.DisplayContentDto;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService extends BaseTranslatableService<BlogDto, BlogTranslationDto> {
    BlogDto create(@Valid BlogDto blogDto, MultipartFile mainImage);


    Page<BlogDto> getBlogsForModeration(Pageable pageable, String query);

    BlogDto getDisplayBlogByIdAndLanguage(Long id, String email, String language);

    Page<BlogDto> getAllDisplayBlogs(int page, int size, String sortBy, String sortDirection);

    List<DisplayContentDto> getRelatedBlogs(Long excludeId, int limit);

    void reject(Long id, String email);

    Page<BlogDto> getContentByCreatorIdBlog(Long id, Pageable pageable, String query);

    Page<BlogDto> getBlogsByStatusForMainPage(String status, String query, int page, int size, String sortBy, String sortDirection, String language);

    Page<BlogDto> getBlogsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection);

    void approve(Long id, String email);

    Page<BlogDto> getHistoryBlog(Long creatorId, Pageable pageable, String query, String status);

    Page<BlogDto> getAllBlogs(Pageable pageable, String query, String status);

    Long countBlogForModeration();

    Blog findByBlogId(Long blogId);

    List<BlogDto> getBlogsByMainPage();
}

package kg.edu.mathbilim.service.interfaces.blog;

import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.dto.blog.CreateBlogDto;
import kg.edu.mathbilim.model.blog.Blog;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogService {
    Blog findBlogById(Long id);

    Boolean existsBlogById(Long id);

    Page<BlogDto> getBlogPage(String query, int page, int size, String sortBy, String sortDirection);

    BlogDto getById(Long id);

    void deleteById(Long id);

    BlogDto createBlog(CreateBlogDto blogDto);

    @Transactional
    void setBlogTranslations(List<BlogTranslationDto> translations, Long blogId);
}

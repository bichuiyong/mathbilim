package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.dto.blog.CreateBlogDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.exception.nsee.FileNotFoundException;
import kg.edu.mathbilim.mapper.blog.BlogMapper;
import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.repository.blog.BlogRepository;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.service.interfaces.blog.BlogTranslationService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;
    private final UserService userService;
    private final BlogTranslationService blogTranslationService;

    @Override
    public Blog findBlogById(Long id) {
        return blogRepository.findById(id).orElseThrow(BlogNotFoundException::new);
    }

    @Override
    public Boolean existsBlogById(Long id) {
        return blogRepository.existsById(id);
    }

    @Override
    public Page<BlogDto> getBlogPage(String query, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return getPage(() -> blogRepository.findAll(pageable));
        }
        return getPage(() -> blogRepository.findAll(pageable));
    }

    @Override
    public BlogDto getById(Long id) {
        return blogMapper.toDto(findBlogById(id));
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        if (Boolean.TRUE.equals(existsBlogById(id))) {
            blogRepository.deleteById(id);
            log.info("Deleted blog with id: {}", id);
        } else {
            throw new BlogNotFoundException();
        }
    }
    @Transactional
    @Override
    public BlogDto createBlog(CreateBlogDto createBlogDto) {
        BlogDto blogDto = createBlogDto.getBlog();
        blogDto.setCreator(userService.getAuthUser());
        blogDto.setStatus(ContentStatus.PENDING_REVIEW);

        Blog blog = blogMapper.toEntity(blogDto);
        Blog savedBlog = blogRepository.save(blog);
        Long saveBlogId = savedBlog.getId();

        List<BlogTranslationDto> translations = blogDto.getBlogTranslations();
        setBlogTranslations(translations, saveBlogId);

        blogRepository.saveAndFlush(blog);

        log.info("Created blog: {}", savedBlog);
        return blogMapper.toDto(savedBlog);

    }

    private Page<BlogDto> getPage(Supplier<Page<Blog>> supplier) {
        return getPage(supplier, "Блоги не были найдены");
    }

    private Page<BlogDto> getPage(Supplier<Page<Blog>> supplier, String notFoundMessage) {
        Page<Blog> page = supplier.get();
        if (page.isEmpty()) {
            throw new FileNotFoundException(notFoundMessage);
        }
        log.info("Получено {} блогов на странице", page.getSize());
        return page.map(blogMapper::toDto);
    }
    @Transactional
    @Override
    public void setBlogTranslations(List<BlogTranslationDto> translations, Long blogId) {
        Set<BlogTranslationDto> filledTranslations = translations.stream()
                .filter(translation ->
                        translation.getTitle() != null && !translation.getTitle().trim().isEmpty() &&
                                translation.getContent() != null && !translation.getContent().trim().isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));


        blogTranslationService.saveTranslations(blogId, filledTranslations);
    }
}

package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.dto.blog.DisplayBlogDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.mapper.blog.BlogMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.repository.blog.BlogRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.service.interfaces.blog.BlogTranslationService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static kg.edu.mathbilim.util.PaginationUtil.getPage;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;
    private final UserService userService;
    private final BlogTranslationService blogTranslationService;
    private final FileService fileService;

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
            return getPage(() -> blogRepository.findAll(pageable), blogMapper::toDto);
        }
        return getPage(() -> blogRepository.findAll(pageable), blogMapper::toDto);
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
    public BlogDto createBlog(BlogDto blogDto, MultipartFile multipartFile) {
        blogDto.setCreator(userService.getAuthUser());
        blogDto.setStatus(ContentStatus.PENDING_REVIEW);

        Blog blog = blogMapper.toEntity(blogDto);
        Blog savedBlog = blogRepository.save(blog);
        Long saveBlogId = savedBlog.getId();

        uploadMainImage(multipartFile, blog);
        List<BlogTranslationDto> translations = blogDto.getBlogTranslations();
        setBlogTranslations(translations, saveBlogId);

        blogRepository.saveAndFlush(blog);

        log.info("Created blog: {}", savedBlog);
        return blogMapper.toDto(savedBlog);

    }

    private void uploadMainImage(MultipartFile mainImage, Blog blog) {
        if (mainImage != null && !mainImage.isEmpty()) {
            File mainImageFile = fileService.uploadFileReturnEntity(
                    mainImage,
                    "blogs/" + blog.getId()
            );
            blog.setMainImage(mainImageFile);
            log.info("Uploaded main image for event {}", blog.getId());
        }
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

    @Override
    public DisplayBlogDto getDisplayBlogById(Long id, String languageCode) {
        return blogRepository.findDisplayBlogById(id, languageCode)
                .orElseThrow(BlogNotFoundException::new);
    }

    @Override
    public Page<DisplayBlogDto> getAllDisplayBlogs(int page, int size, String sortBy, String sortDirection, String languageCode) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        return blogRepository.findAllDisplayBlogsByLanguage(languageCode, pageable);
    }

    @Override
    public List<DisplayBlogDto> getRelatedBlogs(Long excludeId, String languageCode, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return blogRepository.findRelatedBlogs(excludeId, languageCode, pageable);
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        blogRepository.incrementViewCount(id);
        log.debug("View count incremented for blog {}", id);
    }

    @Override
    @Transactional
    public void incrementShareCount(Long id) {
        blogRepository.incrementShareCount(id);
        log.debug("Share count incremented for blog {}", id);
    }
}

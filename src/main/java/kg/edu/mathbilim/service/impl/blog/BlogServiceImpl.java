package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.abstracts.DisplayContentDto;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.mapper.blog.BlogMapper;
import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.repository.blog.BlogRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslatableContentService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.service.interfaces.blog.BlogTranslationService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class BlogServiceImpl extends
        AbstractTranslatableContentService<
                Blog,
                BlogDto,
                BlogTranslationDto,
                BlogRepository,
                BlogMapper,
                BlogTranslationService
                >
        implements BlogService {

    public BlogServiceImpl(BlogRepository repository, BlogMapper mapper, UserService userService, FileService fileService, BlogTranslationService translationService, UserNotificationService notificationService) {
        super(repository, mapper, userService, fileService, translationService, notificationService);
    }

    @Override
    protected RuntimeException getNotFoundException() {
        return new BlogNotFoundException();
    }

    @Override
    protected String getEntityName() {
        return "blog";
    }

    @Override
    protected String getFileUploadPath(Blog entity) {
        return "blogs/" + entity.getId();
    }

    @Override
    protected List<BlogTranslationDto> getTranslationsFromDto(BlogDto dto) {
        return dto.getBlogTranslations();
    }

    @Transactional
    public BlogDto create(BlogDto blogDto, MultipartFile multipartFile) {
        return createBase(blogDto, multipartFile, null);
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        repository.incrementViewCount(id);
        log.debug("View count incremented for blog {}", id);
    }

    @Override
    @Transactional
    public void incrementShareCount(Long id) {
        repository.incrementShareCount(id);
        log.debug("Share count incremented for blog {}", id);
    }

    public DisplayContentDto getDisplayBlogById(Long id) {
        return repository.findDisplayBlogById(id, getCurrentLanguage())
                .orElseThrow(this::getNotFoundException);
    }

    public Page<DisplayContentDto> getAllDisplayBlogs(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        return repository.findAllDisplayBlogsByLanguage(getCurrentLanguage(), pageable);
    }

    public List<DisplayContentDto> getRelatedBlogs(Long excludeId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return repository.findRelatedBlogs(excludeId, getCurrentLanguage(), pageable);
    }

    @Override
    public Page<BlogDto> getBlogsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection) {
        return getContentByStatus(
                status,
                query,
                page,
                size,
                sortBy,
                sortDirection,
                pageable -> repository.findBlogsByStatus(ContentStatus.fromName(status), pageable),
                (q, pageable) -> repository.getBlogsByStatusWithQuery(ContentStatus.fromName(status), q, pageable)
        );
    }

    @Override
    public void approve(Long id) {
        approveContent(id, NotificationEnum.BLOG, "New blog");
    }


}

package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.abstracts.DisplayContentDto;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.mapper.blog.BlogMapper;
import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.user.User;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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


    @Override
    public Page<BlogDto> getBlogsForModeration(Pageable pageable, String query) {

        if (query != null) {
            Page<Blog> allBlogWithQuery = repository.getBlogsByStatusWithQuery(ContentStatus.PENDING_REVIEW, query, pageable);
            return allBlogWithQuery.map(mapper::toDto);
        }
        Page<Blog> blogs = repository.getBlogsByStatus(ContentStatus.PENDING_REVIEW, pageable);

        blogs.forEach(blog -> {
            if (blog.getBlogTranslations() != null) {
                blog.getBlogTranslations().forEach(translation -> {
                    log.info("Blog entity ID: {}, translation languageCode: {}", blog.getId(), translation.getId().getLanguageCode());
                });
            } else {
                log.warn("Blog entity ID: {} has no translations", blog.getId());
            }
        });
        return PaginationUtil.getPage(() -> blogs, mapper::toDto);
    }

    public BlogDto getDisplayBlogById(Long id) {
        Blog blog = repository.findDisplayBlogById(id, getCurrentLanguage())
                .orElseThrow(this::getNotFoundException);

        return mapper.toDto(blog);
    }

    public Page<BlogDto> getAllDisplayBlogs(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        Page<Blog> blogs = repository.findAllDisplayBlogsByLanguageBlog(getCurrentLanguage(), pageable);
        return blogs.map(mapper::toDto);
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
    public void approve(Long id, String email) {
        User user = userService.findByEmail(email);
        approveContent(id, NotificationEnum.BLOG, "New blog", user);
    }

    @Override
    public void reject(Long id, String email) {
        User user = userService.findByEmail(email);
        rejectContent(id, user);
    }


    @Override
    public Page<BlogDto> getContentByCreatorIdBlog(Long creatorId, Pageable pageable, String query) {

        if (query != null) {
            Page<Blog> allBlogWithQuery = repository.getBlogsByStatusWithQuery(ContentStatus.APPROVED, query, pageable);

            return allBlogWithQuery.map(mapper::toDto);
        }

        Page<BlogDto> allBlogs = getContentByCreatorId(creatorId, pageable);

        List<BlogDto> approvedBlogs = allBlogs.stream()
                .filter(blog -> blog.getStatus() == ContentStatus.APPROVED)
                .toList();

        return new PageImpl<>(approvedBlogs, pageable, approvedBlogs.size());
    }

    @Override
    public Page<BlogDto> getHistoryBlog(Long creatorId, Pageable pageable, String query, String status) {
        ContentStatus contentStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                contentStatus = ContentStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status: " + status);
            }
        }

        if (query != null && !query.trim().isEmpty()) {
            if (contentStatus != null) {
                return repository.getBlogsByCreatorAndStatusAndQuery(contentStatus, creatorId, query, pageable)
                        .map(mapper::toDto);
            } else {
                return repository.getBlogsWithQuery(query.trim(), creatorId, pageable)
                        .map(mapper::toDto);
            }
        }

        if (contentStatus != null) {
            return repository.getBlogsByCreatorAndStatus(contentStatus, creatorId, pageable)
                    .map(mapper::toDto);
        }

        return getContentByCreatorId(creatorId, pageable);
    }


    @Override
    public Long countBlogForModeration() {
        return repository.countByStatus(ContentStatus.PENDING_REVIEW);
    }

    @Override
    public Blog findByBlogId(Long blogId) {
        return repository.findById(blogId).orElseThrow(BlogNotFoundException::new);
    }


    @Override
    public List<BlogDto> getBlogsByMainPage() {
        List<Blog> blogs = repository.findTop10ByOrderByCreatedAtDesc();
        return blogs.stream().map(mapper::toDto).toList();
    }
}

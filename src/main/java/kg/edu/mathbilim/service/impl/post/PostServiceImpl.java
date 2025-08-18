package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.PostNotFoundException;
import kg.edu.mathbilim.mapper.post.PostMapper;
import kg.edu.mathbilim.mapper.post.PostMapperImpl;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.post.PostRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslatableContentService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.post.PostTranslationService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostServiceImpl extends
        AbstractTranslatableContentService<
                Post,
                PostDto,
                PostTranslationDto,
                PostRepository,
                PostMapper,
                PostTranslationService
                >
        implements PostService {


    public PostServiceImpl(PostRepository repository, PostMapper mapper, UserService userService, FileService fileService, PostTranslationService translationService, PostRepository postRepository, PostMapperImpl postMapperImpl, UserNotificationService notificationService) {
        super(repository, mapper, userService, fileService, translationService, notificationService);
    }

    @Override
    protected RuntimeException getNotFoundException() {
        return new PostNotFoundException();
    }

    @Override
    protected String getEntityName() {
        return "post";
    }

    @Override
    protected String getFileUploadPath(Post entity) {
        return "posts/" + entity.getId();
    }

    @Override
    protected List<File> uploadEntityFiles(MultipartFile[] attachments, Post entity) {
        return fileService.uploadFilesForPost(attachments, entity);
    }

    @Override
    protected void setEntityFiles(Post entity, List<File> files) {
        entity.setPostFiles(files);
    }

    @Override
    protected List<PostTranslationDto> getTranslationsFromDto(PostDto dto) {
        return dto.getPostTranslations();
    }

    @Transactional
    public PostDto createPost(CreatePostDto createPostDto) {
        return createBase(
                createPostDto.getPost(),
                createPostDto.getImage(),
                createPostDto.getAttachments()
        );
    }

    @Override
    public Long countPostsForModeration() {
        return repository.countByStatus(ContentStatus.PENDING_REVIEW);
    }

    @Transactional
    public void togglePostApproving(Long id) {
        Post post = getEntityById(id);
        ContentStatus status = post.getStatus().equals(ContentStatus.APPROVED) ?
                ContentStatus.REJECTED : ContentStatus.APPROVED;
        post.setStatus(status);
        repository.save(post);
    }

    public Page<PostDto> getUserPosts(Long userId, String query, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return PaginationUtil.getPage(() -> repository.getPostByCreator_Id(userId, pageable), mapper::toDto);
        }
        return PaginationUtil.getPage(() -> repository.getUserPostsWithQuery(userId, query, pageable), mapper::toDto);
    }


    public Page<PostDto> getPostsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection, String lang) {
        return getContentByStatus(
                status,
                query,
                page,
                size,
                sortBy,
                sortDirection,
                pageable -> repository.getPostsByStatus(ContentStatus.fromName(status), pageable),
                (q, pageable) -> repository.getPostsByStatusWithQuery(ContentStatus.fromName(status), q, pageable, lang)
        );
    }

    @Override
    public void approve(Long id, String email) {
        User user = userService.findByEmail(email);
        approveContent(id, NotificationEnum.POST, "New event", user);
    }

    @Override
    public void reject(Long id, String email) {
        User user = userService.findByEmail(email);
        rejectContent(id, user);
    }


    @Override
    @Transactional
    public PostDto getPostById(Long id) {
        Post post = repository.findById(id).orElseThrow(PostNotFoundException::new);
        incrementViewCount(id);
        return mapper.toDto(post);
    }

    @Override
    public Page<PostDto> getPostsByCreator(Long creatorId, Pageable pageable, String query) {
        if (query != null) {
            Page<Post> allPostWithQuery = repository.getPostsByQuery(ContentStatus.APPROVED, query, pageable);

            return allPostWithQuery.map(mapper::toDto);
        }
        Page<PostDto> allPosts = getContentByCreatorId(creatorId, pageable);

        List<PostDto> approvedPosts = allPosts.stream()
                .filter(post -> post.getStatus() == ContentStatus.APPROVED)
                .toList();

        return new PageImpl<>(approvedPosts, pageable, approvedPosts.size());
    }

    @Override
    public Page<PostDto> getHisotryPost(Long creatorId, Pageable pageable, String query, String status) {
        ContentStatus contentStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                contentStatus = ContentStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid content status: " + status);
            }
        }
        if (query != null && !query.isBlank()) {
            if (status != null && !status.isBlank()) {
                return repository.getPostsByStatusAndQuery(
                        contentStatus, query, creatorId, pageable
                ).map(mapper::toDto);
            } else {
                return repository.getUserPostsWithQuery(creatorId, query, pageable)
                        .map(mapper::toDto);
            }
        }

        if (status != null && !status.isBlank()) {
            return repository.findPostByStatus(contentStatus, creatorId, pageable)
                    .map(mapper::toDto);
        }

        return getContentByCreatorId(creatorId, pageable);
    }


    @Override
    public Page<PostDto> getPostsForModeration(Pageable pageable, String query) {
        if (query != null) {
            Page<Post> allPostWithQuery = repository.getPostsByQuery(ContentStatus.PENDING_REVIEW, query, pageable);

            return allPostWithQuery.map(mapper::toDto);
        }

        Page<Post> posts = repository.getPostsByStatus(ContentStatus.PENDING_REVIEW, pageable);
        return PaginationUtil.getPage(() -> posts, mapper::toDto);
    }

    @Override
    public Page<PostDto> getAllPostByStatus(String status, String query, int page, int size, String sortBy, String sortDirection) {
        return getContentByStatus(
                status,
                query,
                page,
                size,
                sortBy,
                sortDirection,
                pageable -> repository.findPostsByStatus(ContentStatus.fromName(status), pageable),
                (q, pageable) -> repository.getPostsByStatus(ContentStatus.fromName(status), q, pageable)
        );
    }

    @Override
    public Post findByPostId(Long id) {
        return repository.findById(id).orElseThrow(PostNotFoundException::new);
    }


    @Override
    public List<PostDto> getPostByMainPage() {
        List<Post> posts = repository.findTop10ByOrderByCreatedAtDesc();

        return posts.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}

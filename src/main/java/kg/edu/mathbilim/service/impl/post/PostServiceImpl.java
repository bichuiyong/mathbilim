package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.FileNotFoundException;
import kg.edu.mathbilim.exception.nsee.PostNotFoundException;
import kg.edu.mathbilim.mapper.post.PostMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.repository.post.PostRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.post.PostTranslationService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final UserService userService;
    private final FileService fileService;
    private final PostTranslationService postTranslationService;

    private Post getEntityById(Long id) {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    @Override
    public PostDto getById(Long id) {
        return postMapper.toDto(getEntityById(id));
    }

    @Override
    public Page<PostDto> getUserPosts(Long userId, String query, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return getPage(() -> postRepository.getPostByCreator_Id(userId, pageable));
        }
        return getPage(() -> postRepository.getUserPostsWithQuery(userId, query, pageable));
    }

    @Transactional
    @Override
    public void togglePostApproving(Long id) {
        Post post = getEntityById(id);
        ContentStatus status = post.getStatus().equals(ContentStatus.APPROVED) ? ContentStatus.REJECTED : ContentStatus.APPROVED;
        post.setStatus(status);
        postRepository.save(post);
    }

    @Override
    public Page<PostDto> getPostsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection) {
        ContentStatus contentStatus = ContentStatus.fromName(status);
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return getPage(() -> postRepository.getPostsByStatus(contentStatus, pageable));
        }
        return getPage(() -> postRepository.getPostsByStatusWithQuery(contentStatus, query, pageable));
    }

    @Override
    public Page<PostDto> getPostPage(String query, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return getPage(() -> postRepository.findAll(pageable));
        }
        return getPage(() -> postRepository.findByQuery(query, pageable));
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
        log.info("Deleted post: {}", id);
    }

    @Override
    public PostDto createPost(CreatePostDto createPostDto) {
        PostDto postDto = createPostDto.getPost();
        postDto.setCreator(userService.getAuthUser());
        postDto.setStatus(ContentStatus.PENDING_REVIEW);

        Post post = postMapper.toEntity(postDto);
        Post savedPost = postRepository.save(post);
        Long savePostId = savedPost.getId();

        List<PostTranslationDto> translations = postDto.getPostTranslations();
        setPostTranslations(translations, savePostId);

        MultipartFile mainImage = createPostDto.getImage() != null && createPostDto.getImage().isEmpty() ? null : createPostDto.getImage();
        uploadMainImage(mainImage, savedPost);

        MultipartFile[] attachments = createPostDto.getAttachments() == null ? new MultipartFile[0] : createPostDto.getAttachments();
        uploadPostFiles(attachments, savedPost);

        postRepository.saveAndFlush(post);

        log.info("Created post: {}", savedPost);
        return postMapper.toDto(savedPost);
    }

    private Page<PostDto> getPage(Supplier<Page<Post>> supplier, String notFoundMessage) {
        Page<Post> page = supplier.get();
        if (page.isEmpty()) {
            throw new FileNotFoundException(notFoundMessage);
        }
        log.info("Получено {} постов на странице", page.getSize());
        return page.map(postMapper::toDto);
    }

    private Page<PostDto> getPage(Supplier<Page<Post>> supplier) {
        return getPage(supplier, "Посты не были найдены");
    }

    private void uploadMainImage(MultipartFile mainImage, Post post) {
        if (mainImage != null && !mainImage.isEmpty()) {
            File mainImageFile = fileService.uploadFileReturnEntity(
                    mainImage,
                    "events/" + post.getId() + "/main"
            );
            post.setMainImage(mainImageFile);
            log.info("Uploaded main image for post {}", post.getId());
        }
    }


    private void uploadPostFiles(MultipartFile[] attachments, Post savedPost) {
        if (attachments.length > 0) {
            List<File> uploadedFiles = fileService.uploadFilesForPost(attachments, savedPost);
            if (!uploadedFiles.isEmpty()) {
                savedPost.setPostFiles(uploadedFiles);
                postRepository.saveAndFlush(savedPost);
                log.info("Uploaded {} files for post {}", uploadedFiles.size(), savedPost.getId());
            }
        }
    }

    private void setPostTranslations(List<PostTranslationDto> translations, Long postId) {
        Set<PostTranslationDto> filledTranslations = translations.stream()
                .filter(translation ->
                        translation.getTitle() != null && !translation.getTitle().trim().isEmpty() &&
                                translation.getContent() != null && !translation.getContent().trim().isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));


        postTranslationService.saveTranslations(postId, filledTranslations);
    }

}

package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.CreatePostDto;
import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.PostNotFoundException;
import kg.edu.mathbilim.mapper.post.PostMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.repository.post.PostRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslatableContentService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.post.PostService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.post.PostTranslationService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    public PostServiceImpl(PostRepository repository, @Qualifier("postMapperImpl") PostMapper mapper, UserService userService, FileService fileService, PostTranslationService translationService) {
        super(repository, mapper, userService, fileService, translationService);
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
        ContentStatus contentStatus = ContentStatus.fromName(status);
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query != null && !query.isEmpty()) {
            System.out.println("Posts with query and lang: " + repository.getPostsByStatusWithQuery(contentStatus, query, pageable, lang).getContent());
            return PaginationUtil.getPage(() -> repository.getPostsByStatusWithQuery(contentStatus, query, pageable, lang), mapper::toDto);
        }

        if (lang != null && !lang.isEmpty()) {
            System.out.println("Posts with lang: " + repository.getPostsByStatusWithLang(contentStatus, lang, pageable).getContent());
            return PaginationUtil.getPage(() -> repository.getPostsByStatusWithLang(contentStatus, lang, pageable), mapper::toDto);
        }

        System.out.println("Posts: " + repository.getPostsByStatus(contentStatus, pageable).getContent());
        return PaginationUtil.getPage(() -> repository.getPostsByStatus(contentStatus, pageable), mapper::toDto);
    }


    @Override
    public PostDto getPostById(Long id) {
        Post post = repository.findById(id).orElseThrow(PostNotFoundException::new);
        return mapper.toDto(post);
    }

    @Override
    public Page<PostDto> getPostsByCreator(Long creatorId, Pageable pageable) {
        Page<PostDto> allPosts = getContentByCreatorId(creatorId, pageable);

        List<PostDto> approvedPosts = allPosts.stream()
                .filter(post -> post.getStatus() == ContentStatus.APPROVED)
                .toList();

        return new PageImpl<>(approvedPosts, pageable, approvedPosts.size());
    }

    @Override
    public Page<PostDto> getPostsForModeration(Pageable pageable) {
        Page<Post> posts = repository.getPostsByStatus(ContentStatus.PENDING_REVIEW, pageable);
        return PaginationUtil.getPage(() -> posts, mapper::toDto);
    }

    @Override
    public Post findByPostId(Long id) {
        return repository.findById(id).orElseThrow(PostNotFoundException::new);
    }

}

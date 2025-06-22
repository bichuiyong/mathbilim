package kg.edu.mathbilim.service.impl.abstracts;

import kg.edu.mathbilim.dto.abstracts.AdminContentDto;
import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.abstracts.AdminContent;
import kg.edu.mathbilim.repository.abstracts.BaseContentRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseContentService;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslationService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractContentService<
        E extends AdminContent,
        D extends AdminContentDto,
        R extends BaseContentRepository<E>,
        M extends BaseMapper<E, D>
        > implements BaseContentService<D> {

    protected final R repository;
    protected final M mapper;
    protected final UserService userService;
    protected final FileService fileService;

    protected abstract RuntimeException getNotFoundException();

    protected abstract String getEntityName();

    protected abstract String getFileUploadPath(E entity);

    protected BaseTranslationService<?> getTranslationService() {
        return null;
    }

    protected List<File> uploadEntityFiles(MultipartFile[] attachments, E entity) {
        return List.of();
    }

    protected void setEntityFiles(E entity, List<File> files) {
    }

    protected E getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(this::getNotFoundException);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public D getById(Long id) {
        return mapper.toDto(getEntityById(id));
    }

    public Page<D> getPage(String query, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return PaginationUtil.getPage(() -> repository.findAll(pageable), mapper::toDto);
        }
        return PaginationUtil.getPage(() -> repository.findByQuery(query, pageable), mapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        BaseTranslationService<?> translationService = getTranslationService();
        if (translationService != null) {
            translationService.deleteAllTranslationsByEntityId(id);
        }

        repository.deleteById(id);
        log.info("Deleted {}: {}", getEntityName(), id);
    }

    protected void uploadMainImage(MultipartFile mainImage, E entity) {
        if (mainImage != null && !mainImage.isEmpty()) {
            File mainImageFile = fileService.uploadFileReturnEntity(
                    mainImage,
                    getFileUploadPath(entity) + "/main"
            );
            entity.setMainImage(mainImageFile);
            log.info("Uploaded main image for {} {}", getEntityName(), entity.getId());
        }
    }

    protected void uploadFiles(MultipartFile[] attachments, E entity) {
        if (attachments != null && attachments.length > 0) {
            List<File> uploadedFiles = uploadEntityFiles(attachments, entity);
            if (!uploadedFiles.isEmpty()) {
                setEntityFiles(entity, uploadedFiles);
                log.info("Uploaded {} files for {} {}", uploadedFiles.size(), getEntityName(), entity.getId());
            }
        }
    }

    protected void setupDtoBeforeSave(D dto) {
        dto.setCreator(userService.getAuthUser());

        if (dto instanceof ContentDto contentDto) {
            contentDto.setStatus(ContentStatus.PENDING_REVIEW);
        }
    }

    protected void processAdditionalFields(Object createDto, E savedEntity) {
    }

    @Transactional
    public D createBase(D dto, MultipartFile mainImage, MultipartFile[] attachments) {
        setupDtoBeforeSave(dto);

        E entity = mapper.toEntity(dto);
        E savedEntity = repository.save(entity);
        Long savedEntityId = savedEntity.getId();

        handleTranslations(dto, savedEntityId);
        uploadMainImage(mainImage, savedEntity);
        uploadFiles(attachments, savedEntity);
        processAdditionalFields(dto, savedEntity);

        repository.saveAndFlush(savedEntity);

        log.info("Created {} with id {}", getEntityName(), savedEntityId);
        return mapper.toDto(savedEntity);
    }

    public String getCurrentLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }

    @Transactional
    public void incrementViewCount(Long id) {
        repository.incrementViewCount(id);
        log.debug("View count incremented for blog {}", id);
    }

    @Transactional
    public void incrementShareCount(Long id) {
        repository.incrementShareCount(id);
        log.debug("Share count incremented for blog {}", id);
    }

    protected abstract void handleTranslations(D dto, Long entityId);
}

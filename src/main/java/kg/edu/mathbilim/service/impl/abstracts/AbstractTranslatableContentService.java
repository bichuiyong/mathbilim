package kg.edu.mathbilim.service.impl.abstracts;

import kg.edu.mathbilim.dto.abstracts.AdminContentDto;
import kg.edu.mathbilim.dto.abstracts.ContentTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.abstracts.AdminContent;
import kg.edu.mathbilim.model.abstracts.Content;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.abstracts.BaseContentRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslatableService;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslationService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import kg.edu.mathbilim.telegram.service.NotificationData;
import kg.edu.mathbilim.telegram.service.NotificationFacade;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.Notification;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
public abstract class AbstractTranslatableContentService<
        E extends AdminContent,
        D extends AdminContentDto,
        T extends ContentTranslationDto,
        R extends BaseContentRepository<E>,
        M extends BaseMapper<E, D>,
        TS extends BaseTranslationService<T>
        > extends AbstractContentService<E, D, R, M>
        implements BaseTranslatableService<D, T> {

    protected final TS translationService;
    protected final NotificationFacade notificationFacade;

    protected AbstractTranslatableContentService(R repository, M mapper, UserService userService,
                                                 FileService fileService, TS translationService,
                                                 NotificationFacade notificationService,
                                                 MessageSource messageSource) {
        super(repository, mapper, userService, fileService, messageSource);
        this.translationService = translationService;
        this.notificationFacade = notificationService;
    }

    @Override
    protected BaseTranslationService<?> getTranslationService() {
        return translationService;
    }

    protected abstract List<T> getTranslationsFromDto(D dto);

    @Override
    protected void handleTranslations(D dto, Long entityId) {
        List<T> translations = getTranslationsFromDto(dto);
        if (translations != null && !translations.isEmpty()) {
            Set<T> translationSet = new LinkedHashSet<>(translations);
            translationService.saveTranslations(entityId, translationSet);
        }
    }


    protected Page<D> getContentByStatus(
            String status,
            String query,
            int page,
            int size,
            String sortBy,
            String sortDirection,
            Function<Pageable, Page<E>> statusFinder,
            BiFunction<String, Pageable, Page<E>> queryFinder
    ) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);

        String safeQuery = query == null ? "" : query;

        log.info("getContentByStatus: status={}, query='{}', page={}, size={}, sortBy={}, sortDirection={}, pageable={}",
                status, safeQuery, page, size, sortBy, sortDirection, pageable);

        Page<E> result = queryFinder.apply(safeQuery, pageable);

        log.info("getContentByStatus executed: returned totalElements={}, totalPages={}, pageNumber={}, pageSize={}, sort={}",
                result.getTotalElements(), result.getTotalPages(), result.getNumber(), result.getSize(), pageable.getSort());

        result.getContent().forEach(e ->
                log.debug("getContentByStatus element: {}", e)
        );

        return result.map(mapper::toDto);
    }



    protected void approveContent(Long id, NotificationEnum notificationType, NotificationData notificationMessage, User users) {
        E content = repository.findById(id).orElseThrow(this::getNotFoundException);
        content.setStatus(ContentStatus.APPROVED);
        ((Content) content).setApprovedBy(users);
        repository.save(content);
        notificationFacade.notifyAllSubscribed(notificationType, notificationMessage);
    }


    protected void rejectContent(Long id, User users) {
        E content = repository.findById(id).orElseThrow(this::getNotFoundException);
        content.setStatus(ContentStatus.REJECTED);
        ((Content) content).setApprovedBy(users);
        repository.save(content);
    }
}

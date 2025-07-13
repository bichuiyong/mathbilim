package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.DisplayEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.EventNotFoundException;
import kg.edu.mathbilim.mapper.event.EventMapper;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;
import kg.edu.mathbilim.repository.event.EventRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslatableContentService;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.event.EventTranslationService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.util.PaginationUtil;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
public class EventServiceImpl extends
        AbstractTranslatableContentService<
                Event,
                EventDto,
                EventTranslationDto,
                EventRepository,
                EventMapper,
                EventTranslationService
                >
        implements EventService {

    private final OrganizationService organizationService;
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository repository, EventMapper mapper, UserService userService, FileService fileService, EventTranslationService translationService, OrganizationService organizationService, EventRepository eventRepository, UserNotificationService notificationService) {
        super(repository, mapper, userService, fileService, translationService, notificationService);
        this.organizationService = organizationService;
        this.eventRepository = eventRepository;
    }

    @Override
    protected RuntimeException getNotFoundException() {
        return new EventNotFoundException();
    }

    @Override
    protected String getEntityName() {
        return "event";
    }

    @Override
    protected String getFileUploadPath(Event entity) {
        return "events/" + entity.getId();
    }

    @Override
    protected List<File> uploadEntityFiles(MultipartFile[] attachments, Event entity) {
        return fileService.uploadFilesForEvent(attachments, entity);
    }

    @Override
    protected void setEntityFiles(Event entity, List<File> files) {
        entity.setEventFiles(files);
    }

    @Override
    protected List<EventTranslationDto> getTranslationsFromDto(EventDto dto) {
        return dto.getEventTranslations();
    }

    @Override
    protected void processAdditionalFields(Object createDto, Event savedEntity) {
        if (createDto instanceof CreateEventDto eventCreateDto) {
            List<Long> organizationIds = eventCreateDto.getOrganizationIds();
            setEventOrganizations(organizationIds, savedEntity);
        }
    }

    @Transactional
    public EventDto create(CreateEventDto createEventDto) {
        return createBase(createEventDto.getEvent(), createEventDto.getImage(), createEventDto.getAttachments());
    }

    @Override
    public DisplayEventDto getDisplayEventById(Long id) {
        DisplayEventDto event = repository.findDisplayEventById(id, getCurrentLanguage())
                .orElseThrow(this::getNotFoundException);
        List<Long> organizationIds = repository.findOrganizationIdsByEventId(id);
        event.setOrganizationIds(organizationIds);
        return event;
    }

    @Override
    public Page<EventDto> getEventsByStatus(String status, String query, int page, int size, String sortBy, String sortDirection) {
        return getContentByStatus(
                status,
                query,
                page,
                size,
                sortBy,
                sortDirection,
                pageable -> repository.findEventsByStatus(ContentStatus.fromName(status), pageable),
                (q, pageable) -> repository.getEventsByStatusWithQuery(ContentStatus.fromName(status), q, pageable)
        );
    }

    @Override
    public void approve(Long id) {
        approveContent(id, NotificationEnum.EVENT, "New event");
    }

    private void setEventOrganizations(List<Long> organizationIds, Event event) {
        if (organizationIds != null && !organizationIds.isEmpty()) {
            List<Organization> organizations = organizationService.addEventToOrganizations(organizationIds, event);
            event.setOrganizations(organizations);
            log.info("Added event {} to {} organizations", event.getId(), organizations.size());
        }
    }

    @Override
    public Page<EventDto> getContentByCreatorIdEvent(Long creatorId, Pageable pageable) {
        Page<EventDto> allEvents = getContentByCreatorId(creatorId, pageable);

        List<EventDto> approvedEvents = allEvents.stream()
                .filter(event -> event.getStatus() == ContentStatus.APPROVED)
                .toList();

        return new PageImpl<>(approvedEvents, pageable, approvedEvents.size());
    }


    @Override
    public Page<EventDto> getEventsForModeration(Pageable pageable) {
        Page<Event> events = repository.getEventsByStatus(ContentStatus.PENDING_REVIEW, pageable);
        return PaginationUtil.getPage(() -> events, mapper::toDto);    }
}
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
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.event.EventRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslatableContentService;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.event.EventTranslationService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.util.PaginationUtil;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
    public void reject(Long id, String email) {
        User user = userService.findByEmail(email);
        rejectContent(id, user);
    }

    @Override
    @Transactional
    public DisplayEventDto getDisplayEventById(Long id) {
        DisplayEventDto event = repository.findDisplayEventById(id, getCurrentLanguage())
                .orElseThrow(this::getNotFoundException);
        List<Long> organizationIds = repository.findOrganizationIdsByEventId(id);
        event.setOrganizationIds(organizationIds);
        incrementViewCount(id);


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
    public void approve(Long id, String email) {
        User user = userService.findByEmail(email);
        approveContent(id, NotificationEnum.EVENT, "New event", user);
    }

    private void setEventOrganizations(List<Long> organizationIds, Event event) {
        if (organizationIds != null && !organizationIds.isEmpty()) {
            List<Organization> organizations = organizationService.addEventToOrganizations(organizationIds, event);
            event.setOrganizations(organizations);
            log.info("Added event {} to {} organizations", event.getId(), organizations.size());
        }
    }

    @Override
    public Page<EventDto> getContentByCreatorIdEvent(Long creatorId, Pageable pageable, String query) {
        if (query != null && !query.isEmpty()) {
            Page<Event> allEventWithQuery = repository.getEventsByStatusWithQuery(ContentStatus.APPROVED, query, pageable);
            return allEventWithQuery.map(mapper::toDto);
        }

        Page<EventDto> allEvents = getContentByCreatorId(creatorId, pageable);

        List<EventDto> approvedEvents = allEvents.stream()
                .filter(event -> event.getStatus() == ContentStatus.APPROVED)
                .toList();

        return new PageImpl<>(approvedEvents, pageable, approvedEvents.size());
    }

    @Override
    public Page<EventDto> getHistoryEvent(Long creatorId, Pageable pageable, String query, String status) {
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
                return repository.getEventsByCreatorAndStatusAndQuery(
                        contentStatus, creatorId, query.trim(), pageable
                ).map(mapper::toDto);
            } else {
                return repository.getEventsWithQuery(
                        query, creatorId, pageable
                ).map(mapper::toDto);
            }
        }

        if (contentStatus != null) {
            return repository.getEventsByStatusAndCreator(contentStatus, creatorId, pageable)
                    .map(mapper::toDto);
        }

        return getContentByCreatorId(creatorId, pageable);
    }


    @Override
    public Long countEventForModeration() {
        return repository.countByStatus(ContentStatus.PENDING_REVIEW);
    }


    @Override
    public Page<EventDto> getEventsForModeration(Pageable pageable, String query) {
        if (query != null && !query.trim().isEmpty()) {
            Page<Event> allEventsWithQuery = repository.getEventsByStatusWithQuery(ContentStatus.PENDING_REVIEW, query, pageable);
            return allEventsWithQuery.map(mapper::toDto);
        }
        Page<Event> events = repository.findEventsByStatus(ContentStatus.PENDING_REVIEW, pageable);
        return PaginationUtil.getPage(() -> events, mapper::toDto);
    }

    @Override
    public Page<EventDto> getAllEvent(String type, String sort, Pageable pageable) {
        Sort sortBy = Sort.by("createdAt").descending();
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortBy);

        Page<Event> events;

        if (type != null && !type.isEmpty()) {
            log.info("Requested type: {}", type);
            boolean offline = type.equalsIgnoreCase("offline");
            log.info("Type as boolean: {}", offline);

            events = repository.getAllEventsByType(offline, sortedPageable);
            log.info("Events fetched: {}", events.getSize());
        } else {
            events = repository.findEventsByStatus(ContentStatus.APPROVED, sortedPageable);
            log.info("Events fetched: {}", events.getSize());
        }

        // Логи для проверки creator перед конвертацией в DTO
        events.forEach(event -> {
            if (event.getCreator() != null) {
                log.info("Event ID: {}, Creator ID: {}, Name: {}", event.getId(),
                        event.getCreator().getId(), event.getCreator().getName());
            } else {
                log.warn("Event ID: {} has null creator!", event.getId());
            }
        });

        Page<EventDto> eventDtos = PaginationUtil.getPage(() -> events, mapper::toDto);

        eventDtos.forEach(dto -> {
            if (dto.getCreator() != null) {
                log.info("DTO Event ID: {}, Creator Name: {}", dto.getId(), dto.getCreator().getName());
            } else {
                log.warn("DTO Event ID: {} has null creator!", dto.getId());
            }
        });

        return eventDtos;
    }


}
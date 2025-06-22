package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.EventNotFoundException;
import kg.edu.mathbilim.mapper.event.EventMapper;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.repository.event.EventRepository;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.event.EventTranslationService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EventTranslationService eventTranslationService;

    private final UserService userService;
    private final FileService fileService;
    private final OrganizationService organizationService;

    private Event getEntityById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(EventNotFoundException::new);
    }

    @Override
    public EventDto getById(Long id) {
        return eventMapper.toDto(getEntityById(id));
    }

    @Override
    public Page<EventDto> getEventPage(String query, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return getPage(() -> eventRepository.findAll(pageable));
        }
//        return getPage(() -> eventRepository.findByQuery(query, pageable));
        return getPage(() -> eventRepository.findAll(pageable));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eventTranslationService.deleteAllTranslationsByEventId(id);
        eventRepository.deleteById(id);
        log.info("Deleted event: {}", id);
    }

    @Transactional
    @Override
    public EventDto create(CreateEventDto createEventDto) {
        EventDto eventDto = createEventDto.getEvent();
        eventDto.setCreator(userService.getAuthUser());
        eventDto.setStatus(ContentStatus.PENDING_REVIEW);

        Event event = eventMapper.toEntity(eventDto);
        Event savedEvent = eventRepository.save(event);
        Long savedEventId = savedEvent.getId();

        List<EventTranslationDto> translations = eventDto.getEventTranslations();
        setEventTranslations(translations, savedEventId);

        MultipartFile mainImage = createEventDto.getImage() != null && createEventDto.getImage().isEmpty() ? null : createEventDto.getImage();
        uploadMainImage(mainImage, savedEvent);

        MultipartFile[] attachments = createEventDto.getAttachments() == null ? new MultipartFile[0] : createEventDto.getAttachments();
        uploadFilesForEvent(attachments, savedEvent);

        List<Long> organizationsIds = createEventDto.getOrganizationIds();
        setEventOrganizations(organizationsIds, savedEvent);

        eventRepository.saveAndFlush(savedEvent);

        log.info("Created event with id {}", savedEventId);
        return eventMapper.toDto(savedEvent);
    }

    private Page<EventDto> getPage(Supplier<Page<Event>> supplier, String notFoundMessage) {
        Page<Event> page = supplier.get();
        if (page.isEmpty()) {
            throw new EventNotFoundException(notFoundMessage);
        }
        log.info("Получено {} мероприятий на странице", page.getSize());
        return page.map(eventMapper::toDto);
    }

    private Page<EventDto> getPage(Supplier<Page<Event>> supplier) {
        return getPage(supplier, "Мероприятия не были найдены");
    }

    private void setEventOrganizations(List<Long> organizationIds, Event event) {
        boolean isNotEmpty = organizationIds != null && !organizationIds.isEmpty();

        if (isNotEmpty) {
            List<Organization> organizations = organizationService.addEventToOrganizations(organizationIds, event);
            event.setOrganizations(organizations);
            log.info("Added event {} to {} organizations", event.getId(), organizations.size());
        }
    }

    private void setEventTranslations(List<EventTranslationDto> translations, Long eventId) {
        Set<EventTranslationDto> filledTranslations = translations.stream()
                .filter(translation ->
                        translation.getTitle() != null && !translation.getTitle().trim().isEmpty() &&
                                translation.getContent() != null && !translation.getContent().trim().isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));


        eventTranslationService.saveTranslations(eventId, filledTranslations);
    }

    private void uploadMainImage(MultipartFile mainImage, Event event) {
        if (mainImage != null && !mainImage.isEmpty()) {
            File mainImageFile = fileService.uploadFileReturnEntity(
                    mainImage,
                    "events/" + event.getId() + "/main"
            );
            event.setMainImage(mainImageFile);
            log.info("Uploaded main image for event {}", event.getId());
        }
    }

    private void uploadFilesForEvent(MultipartFile[] attachments, Event event) {
        if (attachments != null && attachments.length > 0) {
            List<File> uploadedFiles = fileService.uploadFilesForEvent(
                    attachments,
                    event
            );
            if (!uploadedFiles.isEmpty()) {
                event.setEventFiles(uploadedFiles);
                log.info("Uploaded {} files for event {}", uploadedFiles.size(), event.getId());
            }
        }
    }
}
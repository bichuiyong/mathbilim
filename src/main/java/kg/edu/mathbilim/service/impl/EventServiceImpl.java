package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.EventDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.EventNotFoundException;
import kg.edu.mathbilim.mapper.EventMapper;
import kg.edu.mathbilim.model.Event;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.repository.EventRepository;
import kg.edu.mathbilim.service.interfaces.EventService;
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

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

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
        return getPage(() -> eventRepository.findByQuery(query, pageable));
    }

    @Override
    public void delete(Long id) {
        eventRepository.deleteById(id);
        log.info("Deleted event: {}", id);
    }

    @Transactional
    @Override
    public EventDto create(EventDto eventDto, MultipartFile mainImage, MultipartFile[] attachments, List<Long> organizationIds) {
        eventDto.setUser(userService.getAuthUser());
        eventDto.setStatus(ContentStatus.PENDING_REVIEW);

        Event event = eventMapper.toEntity(eventDto);
        Event savedEvent = eventRepository.save(event);

        if (mainImage != null && !mainImage.isEmpty()) {
            File mainImageFile = fileService.uploadFileReturnEntity(
                    mainImage,
                    "events/" + savedEvent.getId() + "/main",
                    userService.getAuthUserEntity()
            );
            savedEvent.setMainImage(mainImageFile);
            log.info("Uploaded main image for event {}", savedEvent.getId());
        }

        if (attachments != null && attachments.length > 0) {
            Set<File> uploadedFiles = fileService.uploadFilesForEvent(
                    attachments,
                    savedEvent,
                    userService.getAuthUserEntity()
            );
            if (!uploadedFiles.isEmpty()) {
                savedEvent.setFiles(uploadedFiles);
                log.info("Uploaded {} files for event {}", uploadedFiles.size(), savedEvent.getId());
            }
        }

        if (organizationIds != null && !organizationIds.isEmpty()) {
            Set<Organization> organizations = organizationService.addEventToOrganizations(organizationIds, savedEvent);
            savedEvent.setOrganizations(organizations);
            log.info("Added event {} to {} organizations", savedEvent.getId(), organizations.size());
        }

        Event finalEvent = eventRepository.saveAndFlush(savedEvent);
        EventDto result = eventMapper.toDto(finalEvent);
        log.info("Created event: {}", finalEvent.getName());

        return result;
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
}
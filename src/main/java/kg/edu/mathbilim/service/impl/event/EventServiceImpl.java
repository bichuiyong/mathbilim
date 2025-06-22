package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.exception.nsee.EventNotFoundException;
import kg.edu.mathbilim.mapper.event.EventMapper;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.repository.event.EventRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslatableContentService;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.event.EventTranslationService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
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

    public EventServiceImpl(EventRepository repository, EventMapper mapper, UserService userService, FileService fileService, EventTranslationService translationService, OrganizationService organizationService) {
        super(repository, mapper, userService, fileService, translationService);
        this.organizationService = organizationService;
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

    private void setEventOrganizations(List<Long> organizationIds, Event event) {
        if (organizationIds != null && !organizationIds.isEmpty()) {
            List<Organization> organizations = organizationService.addEventToOrganizations(organizationIds, event);
            event.setOrganizations(organizations);
            log.info("Added event {} to {} organizations", event.getId(), organizations.size());
        }
    }
}
package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.EventTypeDto;
import kg.edu.mathbilim.dto.event.EventTypeTranslationDto;
import kg.edu.mathbilim.mapper.event.EventTypeMapper;
import kg.edu.mathbilim.model.event.EventType;
import kg.edu.mathbilim.model.event.EventTypeTranslation;
import kg.edu.mathbilim.repository.event.EventTypeRepository;
import kg.edu.mathbilim.repository.event.EventTypeTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTypeContentService;
import kg.edu.mathbilim.service.interfaces.event.EventTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventTypeServiceImpl
        extends AbstractTypeContentService<
        EventType,
        EventTypeDto,
        EventTypeTranslation,
        EventTypeTranslationDto,
        EventTypeRepository,
        EventTypeTranslationRepository,
        EventTypeMapper>
        implements EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    private final EventTypeMapper eventTypeMapper;

    public EventTypeServiceImpl(EventTypeRepository repository,
                                EventTypeTranslationRepository translationRepository,
                                EventTypeMapper mapper) {
        super(repository, translationRepository, mapper);
        this.eventTypeRepository = repository;
        this.eventTypeMapper = mapper;
    }

    @Override
    public List<EventTypeDto> getAllEventTypes() {
        return getAll();
    }

    @Override
    public EventTypeDto getEventTypeById(Integer id) {
        return getByIdOrThrow(id);
    }

    @Override
    public List<EventTypeDto> getEventTypesByLanguage(String languageCode) {
        return getByLanguage(languageCode);
    }

    @Transactional
    @Override
    public EventTypeDto createEventType(EventTypeDto eventTypeDto) {
        return create(eventTypeDto);
    }

    @Transactional
    @Override
    public EventTypeDto updateEventType(Integer id, EventTypeDto eventTypeDto) {
        return update(id, eventTypeDto);
    }

    @Transactional
    @Override
    public void deleteEventType(Integer id) {
        delete(id);
    }

    @Transactional
    @Override
    public EventTypeDto addTranslation(Integer eventTypeId, String languageCode, String translation) {
        return addTranslation(eventTypeId, languageCode, translation);
    }

    @Transactional
    @Override
    public EventTypeDto removeTranslation(Integer eventTypeId, String languageCode) {
        return removeTranslation(eventTypeId, languageCode);
    }

    @Override
    public List<EventTypeDto> getAllEventTypesByQuery(String name, String lang) {
       return getAllByQuery(name, lang);
//        return eventTypeRepository.findAllByQuery(name, lang).stream()
//                .map(eventTypeMapper::toDto)
//                .toList();
    }

    @Override
    protected EventType createNewEntity() {
        return new EventType();
    }

    @Override
    protected EventTypeTranslationDto createTranslationDto(Integer typeId, String languageCode, String translation) {
        return EventTypeTranslationDto.builder()
                .typeId(typeId)
                .languageCode(languageCode)
                .translation(translation)
                .build();
    }

    @Override
    protected void setTypeIdInTranslation(EventTypeTranslationDto translationDto, Integer typeId) {
        translationDto.setTypeId(typeId);
    }
}
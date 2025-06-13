package kg.edu.mathbilim.service.impl.event.event_type;

import kg.edu.mathbilim.dto.event.event_type.EventTypeDto;
import kg.edu.mathbilim.dto.event.event_type.EventTypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TypeNotFoundException;
import kg.edu.mathbilim.mapper.event.event_type.EventTypeMapper;
import kg.edu.mathbilim.model.event.event_type.EventType;
import kg.edu.mathbilim.repository.event.event_type.EventTypeRepository;
import kg.edu.mathbilim.service.interfaces.event.event_type.EventTypeService;
import kg.edu.mathbilim.service.interfaces.event.event_type.EventTypeTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    private final EventTypeMapper eventTypeMapper;
    private final EventTypeTranslationService eventTypeTranslationService;

    @Override
    public List<EventTypeDto> getAllEventTypes() {
        return eventTypeRepository.findAll()
                .stream()
                .map(eventTypeMapper::toDto)
                .toList();
    }

    private EventType getEventTypeEntity(Integer id) {
        return eventTypeRepository.findById(id)
                .orElseThrow(TypeNotFoundException::new);
    }

    @Override
    public EventTypeDto getEventTypeById(Integer id) {
        return eventTypeMapper.toDto(getEventTypeEntity(id));
    }

    @Override
    public List<EventTypeDto> getEventTypesByLanguage(String languageCode) {
        return eventTypeRepository.findAll().stream()
                .map(eventType -> {
                    EventTypeDto dto = eventTypeMapper.toDto(eventType);
                    dto .setEventTypeTranslations(List.of(eventTypeTranslationService.getTranslation(eventType.getId(), languageCode)));
                    return dto;
                })
                .toList();
    }

    @Transactional
    @Override
    public EventTypeDto createEventType(EventTypeDto eventTypeDto) {
        EventType eventType = new EventType();
        EventType savedEventType = eventTypeRepository.save(eventType);

        EventTypeDto savedDto = eventTypeMapper.toDto(savedEventType);

        if (eventTypeDto.getEventTypeTranslations() != null && !eventTypeDto.getEventTypeTranslations().isEmpty()) {
            List<EventTypeTranslationDto> savedTranslations = eventTypeDto
                    .getEventTypeTranslations()
                    .stream()
                    .map(translation -> {
                        translation.setEventTypeId(savedEventType.getId());
                        return eventTypeTranslationService.createTranslation(translation);
                    })
                    .toList();
            savedDto.setEventTypeTranslations(savedTranslations);
        }

        return savedDto;
    }

    @Transactional
    @Override
    public EventTypeDto updateEventType(Integer id, EventTypeDto eventTypeDto) {
        EventTypeDto dto = getEventTypeById(id);

        if (eventTypeDto.getEventTypeTranslations() != null) {
            eventTypeTranslationService.deleteAllTranslationsByEventTypeId(id);

            List<EventTypeTranslationDto> savedTranslations =
                    eventTypeDto.getEventTypeTranslations()
                            .stream()
                            .map(translation -> {
                                translation.setEventTypeId(id);
                                return eventTypeTranslationService.createTranslation(translation);
                            })
                            .toList();

            dto.setEventTypeTranslations(savedTranslations);
            return dto;
        }

        return dto;
    }

    @Transactional
    @Override
    public void deleteEventType(Integer id) {
        eventTypeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public EventTypeDto addTranslation(Integer eventTypeId, String languageCode, String translation) {
        EventTypeDto eventType = getEventTypeById(eventTypeId);

        EventTypeTranslationDto translationDto = EventTypeTranslationDto.builder()
                .eventTypeId(eventTypeId)
                .languageCode(languageCode)
                .translation(translation)
                .build();

        eventTypeTranslationService.upsertTranslation(translationDto);
        eventType.getEventTypeTranslations().add(translationDto);
        return eventType;
    }

    @Transactional
    @Override
    public EventTypeDto removeTranslation(Integer eventTypeId, String languageCode) {
        getEventTypeById(eventTypeId);
        eventTypeTranslationService.deleteTranslation(eventTypeId, languageCode);
        return getEventTypeById(eventTypeId);
    }
}
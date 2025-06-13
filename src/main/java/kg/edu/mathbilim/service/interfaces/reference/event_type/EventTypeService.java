package kg.edu.mathbilim.service.interfaces.reference.event_type;


import kg.edu.mathbilim.dto.reference.event_type.EventTypeDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventTypeService {

    List<EventTypeDto> getAllEventTypes();

    EventTypeDto getEventTypeById(Integer id);

    List<EventTypeDto> getEventTypesByLanguage(String languageCode);

    @Transactional
    EventTypeDto createEventType(EventTypeDto eventTypeDto);

    @Transactional
    EventTypeDto updateEventType(Integer id, EventTypeDto eventTypeDto);

    @Transactional
    void deleteEventType(Integer id);

    @Transactional
    EventTypeDto addTranslation(Integer eventTypeId, String languageCode, String translation);

    @Transactional
    EventTypeDto removeTranslation(Integer eventTypeId, String languageCode);
}

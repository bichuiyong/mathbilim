package kg.edu.mathbilim.service.interfaces;


import kg.edu.mathbilim.dto.EventTypeDto;

import java.util.List;

public interface EventTypeService {
    EventTypeDto getEventTypeByName(String name);

    List<EventTypeDto> getAllEventTypes();

    boolean existsByEventType(String eventType);

    EventTypeDto createEventType(EventTypeDto eventTypeDto);

    void deleteEventType(Integer eventTypeDto);

    EventTypeDto updateEventType(EventTypeDto eventTypeDto);
}

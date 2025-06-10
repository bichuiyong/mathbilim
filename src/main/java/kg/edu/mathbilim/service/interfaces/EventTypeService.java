package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.model.Category;
import kg.edu.mathbilim.model.EventType;

import java.util.List;

public interface EventTypeService {
    EventType getEventTypeByName(String name);
    List<EventType> getAllEventTypes();
}

package kg.edu.mathbilim.components;

import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.repository.event.EventRepository;
import org.springframework.stereotype.Component;

@Component("eventSecurity")
public class EventSecurity extends ContentSecurity<Event, EventRepository> {
    public EventSecurity(EventRepository eventRepository) {
        super(eventRepository);
    }
}

package kg.edu.mathbilim.components;

import kg.edu.mathbilim.repository.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventSecurity {
    @Autowired
    private EventRepository eventRepository;


    public boolean isOwner(long id, String username) {
    return eventRepository.findById(id)
            .map(e->e.getCreator().getEmail().equals(username))
            .orElse(false);

    }
}

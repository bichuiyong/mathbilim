package kg.edu.mathbilim.components;

import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.repository.event.EventRepository;
import kg.edu.mathbilim.repository.user.UserRepository;
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

package kg.edu.mathbilim.repository.event;

import kg.edu.mathbilim.model.event.event_type.EventType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
}

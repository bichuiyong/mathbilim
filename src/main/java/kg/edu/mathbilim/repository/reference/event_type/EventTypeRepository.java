package kg.edu.mathbilim.repository.reference.event_type;

import kg.edu.mathbilim.model.reference.event_type.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
    Optional<EventType> findByName(String name);
}

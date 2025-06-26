package kg.edu.mathbilim.repository.event;

import kg.edu.mathbilim.model.event.EventType;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventTypeRepository extends AbstractTypeRepository<EventType> {
}

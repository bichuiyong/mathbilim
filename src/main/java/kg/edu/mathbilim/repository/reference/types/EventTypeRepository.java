package kg.edu.mathbilim.repository.reference.types;

import kg.edu.mathbilim.model.reference.types.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
}

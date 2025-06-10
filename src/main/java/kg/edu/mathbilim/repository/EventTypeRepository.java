package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
    Optional<EventType> findByName(String name);
}

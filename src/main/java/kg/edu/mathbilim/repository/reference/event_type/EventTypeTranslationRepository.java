package kg.edu.mathbilim.repository.reference.event_type;

import kg.edu.mathbilim.model.reference.event_type.EventTypeTranslation;
import kg.edu.mathbilim.model.reference.event_type.EventTypeTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeTranslationRepository extends JpaRepository<EventTypeTranslation, EventTypeTranslationId> {
}

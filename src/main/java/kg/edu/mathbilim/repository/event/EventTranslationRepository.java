package kg.edu.mathbilim.repository.event;

import kg.edu.mathbilim.model.event.EventTranslation;
import kg.edu.mathbilim.model.event.EventTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTranslationRepository extends JpaRepository<EventTranslation, EventTranslationId> {
}

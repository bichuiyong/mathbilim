package kg.edu.mathbilim.repository.event;

import kg.edu.mathbilim.model.event.EventTranslation;
import kg.edu.mathbilim.model.event.EventTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventTranslationRepository extends JpaRepository<EventTranslation, EventTranslationId> {

    @Query("SELECT et FROM EventTranslation et WHERE et.id.eventId = :eventId")
    List<EventTranslation> findByEventId(@Param("eventId") Long eventId);

    @Query("SELECT et FROM EventTranslation et WHERE et.id.languageCode = :languageCode")
    List<EventTranslation> findByIdLanguageCode(@Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM EventTranslation et WHERE et.id.eventId = :eventId")
    void deleteByEventId(@Param("eventId") Long eventId);
}
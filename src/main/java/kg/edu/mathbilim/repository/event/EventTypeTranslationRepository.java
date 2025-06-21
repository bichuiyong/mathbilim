package kg.edu.mathbilim.repository.event;

import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import kg.edu.mathbilim.model.event.event_type.EventTypeTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventTypeTranslationRepository extends JpaRepository<EventTypeTranslation, TypeTranslation.TranslationId> {

    @Query("SELECT ett FROM EventTypeTranslation ett WHERE ett.id.typeId = :eventTypeId")
    List<EventTypeTranslation> findByEventTypeId(@Param("eventTypeId") Integer eventTypeId);

    @Query("SELECT ett FROM EventTypeTranslation ett WHERE ett.id.languageCode = :languageCode")
    List<EventTypeTranslation> findByLanguageCode(@Param("languageCode") String languageCode);

    @Query("SELECT ett FROM EventTypeTranslation ett WHERE ett.id.typeId = :eventTypeId AND ett.id.languageCode = :languageCode")
    Optional<EventTypeTranslation> findByEventTypeIdAndLanguageCode(@Param("eventTypeId") Integer eventTypeId,
                                                                    @Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM EventTypeTranslation ett WHERE ett.id.typeId = :eventTypeId")
    void deleteByEventTypeId(@Param("eventTypeId") Integer eventTypeId);

    @Query("SELECT CASE WHEN COUNT(ett) > 0 THEN true ELSE false END FROM EventTypeTranslation ett " +
            "WHERE ett.id.typeId = :eventTypeId AND ett.id.languageCode = :languageCode")
    boolean existsByEventTypeIdAndLanguageCode(@Param("eventTypeId") Integer eventTypeId,
                                               @Param("languageCode") String languageCode);
}
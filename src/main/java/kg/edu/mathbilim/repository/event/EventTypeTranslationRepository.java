package kg.edu.mathbilim.repository.event;

import kg.edu.mathbilim.model.event.EventTypeTranslation;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeTranslationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventTypeTranslationRepository
        extends AbstractTypeTranslationRepository<EventTypeTranslation> {
//
//    default List<EventTypeTranslation> findByEventTypeId(Integer eventTypeId) {
//        return findByTypeId(eventTypeId);
//    }

//    default Optional<EventTypeTranslation> findByEventTypeIdAndLanguageCode(
//            Integer eventTypeId, String languageCode) {
//        return findByTypeIdAndLanguageCode(eventTypeId, languageCode);
//    }
//
//    default void deleteByEventTypeId(Integer eventTypeId) {
//        deleteByTypeId(eventTypeId);
//    }
//
//    default boolean existsByEventTypeIdAndLanguageCode(
//            Integer eventTypeId, String languageCode) {
//        return existsByTypeIdAndLanguageCode(eventTypeId, languageCode);
//    }
}
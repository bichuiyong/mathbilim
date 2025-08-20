package kg.edu.mathbilim.repository.event;

import kg.edu.mathbilim.model.event.EventType;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EventTypeRepository extends AbstractTypeRepository<EventType> {
    @Override
    @Query(value = "SELECT DISTINCT ON (e.id) e.*\n" +
            "FROM event_types e\n" +
            "JOIN event_type_translations et ON e.id = et.type_id\n" +
            "WHERE (:lang IS NULL OR et.language_code = :lang)\n" +
            "AND (:name IS NULL OR LOWER(et.translation) LIKE CONCAT(LOWER(:name), '%'))\n" +
            "ORDER BY e.id, et.type_id", nativeQuery = true)
    List<EventType> findAllByQuery(@Param("name") String name, @Param("lang") String lang);



}

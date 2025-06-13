package kg.edu.mathbilim.repository.event;

import kg.edu.mathbilim.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
//    @Query("SELECT e FROM Event e WHERE " +
//            "LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(e.content) LIKE LOWER(CONCAT('%', :query, '%'))")
//    Page<Event> findByQuery(@Param("query") String query, Pageable pageable);

}

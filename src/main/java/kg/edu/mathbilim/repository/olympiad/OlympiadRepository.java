package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.model.olympiad.Olympiad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OlympiadRepository extends JpaRepository<Olympiad, Long> {
    List<Olympiad> findTop10ByOrderByCreatedAtDesc();
}

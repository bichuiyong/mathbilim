package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.model.olympiad.Olympiad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OlympiadRepository extends JpaRepository<Olympiad, Long> {
    List<Olympiad> findTop10ByDeletedFalseOrderByCreatedAtDesc();
    @Query("SELECT o FROM Olympiad o WHERE o.deleted = false")
    Page<Olympiad> findAllActive(Pageable pageable);

    Optional<Olympiad> findByIdAndDeletedFalse(Long id);

    @Query("update Olympiad o set o.deleted = true where o.id = :id and o.deleted = false")
    void deleteByIdAndDeletedFalse(Long id);

}

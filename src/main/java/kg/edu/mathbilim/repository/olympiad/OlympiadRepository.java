package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.model.olympiad.Olympiad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OlympiadRepository extends JpaRepository<Olympiad, Long> {
    List<Olympiad> findTop10ByDeletedFalseOrderByCreatedAtDesc();
    @Query("SELECT o FROM Olympiad o WHERE o.deleted = false")
    Page<Olympiad> findAllActive(Pageable pageable);

    Optional<Olympiad> findByIdAndDeletedFalse(Long id);

    @Modifying
    @Transactional
    @Query("update Olympiad o set o.deleted = true where o.id = :id and o.deleted = false")
    void deleteByIdAndDeletedFalse(Long id);


    @Query(value = "select distinct o from Olympiad o where LOWER(o.title) LIKE LOWER(CONCAT('%', :query, '%')) and o.deleted = false", nativeQuery = true)
    Page<Olympiad> getOlympiadByQuery(@Param("query") String query, Pageable pageable);

    Page<Olympiad> findAllByDeletedFalse(Pageable pageable);

}

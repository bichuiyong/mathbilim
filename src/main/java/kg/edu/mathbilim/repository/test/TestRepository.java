package kg.edu.mathbilim.repository.test;

import kg.edu.mathbilim.model.test.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {

    Page<Test> findByNameContainingIgnoreCaseAndDeletedFalse(String name, Pageable pageable);

    List<Test> findAllByDeletedFalse();

    Page<Test> findAllByDeletedFalse(Pageable pageable);

    Optional<Test> findByIdAndDeletedFalse(Long id);

    @Query("update Olympiad o set o.deleted=true where o.id = :id")
    void deleteByIdAndDeletedFalse(Long id);
}

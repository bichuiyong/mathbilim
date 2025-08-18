package kg.edu.mathbilim.repository.test;

import kg.edu.mathbilim.model.test.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

    Page<Test> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

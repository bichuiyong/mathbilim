package kg.edu.mathbilim.repository.test;

import kg.edu.mathbilim.model.test.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
}

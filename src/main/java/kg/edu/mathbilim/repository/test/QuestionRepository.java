package kg.edu.mathbilim.repository.test;

import kg.edu.mathbilim.model.test.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByTestIdAndNumberOrder(Long id, Integer numberOrder);
}

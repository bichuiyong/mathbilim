package kg.edu.mathbilim.repository.test;

import kg.edu.mathbilim.model.test.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}

package kg.edu.mathbilim.repository.test;

import kg.edu.mathbilim.model.test.Attempt;
import kg.edu.mathbilim.model.test.AttemptAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptAnswerRepository extends JpaRepository<AttemptAnswer, Long> {
    List<AttemptAnswer> findAllByAttempt_Id(Long attemptId);

    List<AttemptAnswer> findAllByAttempt(Attempt attempt);


}

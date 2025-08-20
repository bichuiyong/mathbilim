package kg.edu.mathbilim.repository.test;

import kg.edu.mathbilim.model.test.Attempt;
import kg.edu.mathbilim.model.test.Test;
import kg.edu.mathbilim.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    Optional<Attempt> findFirstByUserAndTestOrderByIdDesc(User user, Test test);

    @Query("SELECT a FROM Attempt a " +
            "JOIN FETCH a.attemptAnswers aa " +
            "JOIN FETCH aa.question q " +
            "JOIN FETCH q.topic " +
            "WHERE a.id = :id")
    Optional<Attempt> findByIdWithAnswersAndTopics(@Param("id") Long id);

    List<Attempt> findTop5ByUserAndTestIdOrderByFinishedAtDesc(User user, Long testId);
}

package kg.edu.mathbilim.repository.test;

import kg.edu.mathbilim.model.test.Attempt;
import kg.edu.mathbilim.model.test.Test;
import kg.edu.mathbilim.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    Optional<Attempt> findFirstByUserAndTestOrderByIdDesc(User user, Test test);

}

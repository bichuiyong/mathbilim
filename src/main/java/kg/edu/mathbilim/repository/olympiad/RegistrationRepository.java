package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.model.olympiad.Registration;
import kg.edu.mathbilim.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByOlympiadStageAndUser(OlympiadStage olympiadStage, User user);
}

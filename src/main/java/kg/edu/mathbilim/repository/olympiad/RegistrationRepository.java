package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.model.olympiad.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

}

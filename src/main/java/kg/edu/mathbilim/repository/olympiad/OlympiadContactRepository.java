package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.model.olympiad.OlympiadContact;
import kg.edu.mathbilim.model.olympiad.OlympiadContactKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OlympiadContactRepository extends JpaRepository<OlympiadContact, OlympiadContactKey> {
}

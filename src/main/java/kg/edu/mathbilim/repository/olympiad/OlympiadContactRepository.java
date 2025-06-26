package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.model.olympiad.OlympiadContact;
import kg.edu.mathbilim.model.olympiad.OlympiadContactId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OlympiadContactRepository extends JpaRepository<OlympiadContact, OlympiadContactId> {
}

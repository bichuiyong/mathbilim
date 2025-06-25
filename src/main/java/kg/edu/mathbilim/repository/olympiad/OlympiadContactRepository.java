package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.model.olympiad.OlympiadContact;
import kg.edu.mathbilim.model.olympiad.OlympiadContactId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OlympiadContactRepository extends JpaRepository<OlympiadContact, OlympiadContactId> {
    List<OlympiadContact> getByOlympiad_Id(Integer olympiadId);
}

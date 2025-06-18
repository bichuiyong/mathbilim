package kg.edu.mathbilim.repository.organization;

import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OlympOrganizationRepository extends JpaRepository<OlympiadOrganization, Integer> {
    List<OlympiadOrganization> findAllByOlympiad_Id(Integer olympiadId);
    List<OlympiadOrganization> findAllByOrganization_Id(Long organizationId);
}

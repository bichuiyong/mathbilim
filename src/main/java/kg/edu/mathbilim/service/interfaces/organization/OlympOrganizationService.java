package kg.edu.mathbilim.service.interfaces.organization;

import kg.edu.mathbilim.dto.organization.OlympOrganizationDto;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OlympOrganizationService {
    @Transactional
    void addOrganizationToOlympiad(OlympOrganizationDto dto);

    List<OlympiadOrganization> getByOlympiadId(Long olympiadId);

    List<OlympiadOrganization> getByOrganizationId(Long olympiadId);
}

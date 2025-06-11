package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.model.Event;
import kg.edu.mathbilim.model.Organization;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface OrganizationService {
    OrganizationDto getById(Long id);

    List<OrganizationDto> getOrganizations(String query);

    @Transactional
    Set<Organization> addEventToOrganizations(List<Long> organizationIds, Event event);

    OrganizationDto create(OrganizationDto dto, MultipartFile avatarFile);
}

package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.Organization;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrganizationService {
    OrganizationDto getById(Long id);

    List<OrganizationDto> getOrganizations(String query);

    List<OrganizationDto> getByIds(List<Long> ids);

    @Transactional
    List<Organization> addEventToOrganizations(List<Long> organizationIds, Event event);

    OrganizationDto create(OrganizationDto dto, MultipartFile avatarFile);
}

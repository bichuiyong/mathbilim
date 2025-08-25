package kg.edu.mathbilim.service.interfaces;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.organization.OrganizationIdNameDto;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.Organization;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrganizationService {
    List<OrganizationIdNameDto> getAllOrganizationIdNames();

    OrganizationDto getById(Long id);

    Organization getByIdModel(Long id);

    List<OrganizationDto> getOrganizations(String query);

    List<OrganizationDto> getByIds(List<Long> ids);

    @Transactional
    List<Organization> addEventToOrganizations(List<Long> organizationIds, Event event);

    OrganizationDto create(@Valid OrganizationDto dto, MultipartFile avatarFile);

    Page<OrganizationDto> getOrganizations(String query, Pageable pageable);

    @Transactional
    OrganizationDto update(Long id, @Valid OrganizationDto dto, MultipartFile avatarFile);

    @Transactional
    boolean delete(Long id);
}

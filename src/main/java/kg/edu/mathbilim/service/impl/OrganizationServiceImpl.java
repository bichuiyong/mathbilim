package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.organization.OrganizationIdNameDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.OrganizationNotFound;
import kg.edu.mathbilim.mapper.OrganizationMapper;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.repository.OrganizationRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final FileService fileService;
    private final UserService userService;

    private Organization getEntityById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(OrganizationNotFound::new);
    }

    @Override
    public List<OrganizationIdNameDto> getAllOrganizationIdNames() {
        return organizationRepository.findAll().stream().map(organization -> OrganizationIdNameDto
                .builder()
                .id(organization.getId())
                .name(organization.getName())
                .build())
                .toList();
    }

    @Override
    public OrganizationDto getById(Long id) {
        return organizationMapper.toDto(getEntityById(id));
    }

    @Override
    public Organization getByIdModel(Long id) {
        return organizationRepository.getById(id);
    }

    @Override
    public List<OrganizationDto> getOrganizations(String query) {
        if (query != null && !query.isEmpty()) {
            return organizationRepository.findByNameStartingWith(query)
                    .stream()
                    .map(organizationMapper::toDto)
                    .toList();
        }
        return organizationRepository.findAll()
                .stream()
                .map(organizationMapper::toDto)
                .toList();
    }

    @Override
    public List<OrganizationDto> getByIds(List<Long> ids){
        List<OrganizationDto> organizations = new LinkedList<>();

        for (Long orgId : ids) {
            OrganizationDto organization = getById(orgId);
            organizations.add(organization);
        }

        return organizations;
    }

    @Transactional
    @Override
    public List<Organization> addEventToOrganizations(List<Long> organizationIds, Event event) {
        List<Organization> organizations = new LinkedList<>();

        for (Long orgId : organizationIds) {
            Organization organization = getEntityById(orgId);

            organization.setEvents(new LinkedList<>(Collections.singleton(event)));
            organizationRepository.saveAndFlush(organization);
            organizations.add(organization);

            log.info("Added event {} to organization {}", event.getId(), organization.getName());
        }

        return organizations;
    }

    @Transactional
    @Override
    public OrganizationDto create(OrganizationDto dto, MultipartFile avatarFile) {
        dto.setCreator(userService.getAuthUser());
        dto.setStatus(ContentStatus.PENDING_REVIEW);

        if (avatarFile != null) {
            FileDto avatar = fileService.uploadAvatar(avatarFile);
            dto.setAvatar(avatar);
        }

        Organization organization = organizationMapper.toEntity(dto);
        organizationRepository.save(organization);
        return organizationMapper.toDto(organization);
    }
}

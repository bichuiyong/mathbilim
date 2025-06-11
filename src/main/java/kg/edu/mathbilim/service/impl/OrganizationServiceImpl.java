package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.OrganizationNotFound;
import kg.edu.mathbilim.mapper.OrganizationMapper;
import kg.edu.mathbilim.model.Event;
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
    public OrganizationDto getById(Long id) {
        return organizationMapper.toDto(getEntityById(id));
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

    @Transactional
    @Override
    public Set<Organization> addEventToOrganizations(List<Long> organizationIds, Event event) {
        Set<Organization> organizations = new LinkedHashSet<>();

        for (Long orgId : organizationIds) {
            Organization organization = getEntityById(orgId);

            organization.setEvents(new LinkedHashSet<>(Collections.singleton(event)));
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
            FileDto avatar = fileService.uploadAvatar(avatarFile, userService.getAuthUserEntity());
            dto.setAvatar(avatar);
        }

        Organization organization = organizationMapper.toEntity(dto);
        organizationRepository.save(organization);
        return organizationMapper.toDto(organization);
    }
}

package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.organization.OrganizationIdNameDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.OrganizationNotFound;
import kg.edu.mathbilim.mapper.OrganizationMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.repository.OrganizationRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
        return organizationRepository.findByIdAndByDeletedFalse(id)
                .orElseThrow(OrganizationNotFound::new);
    }

    @Override
    public List<OrganizationIdNameDto> getAllOrganizationIdNames() {
        return organizationRepository.findAllByDeletedFalse().stream().map(organization -> OrganizationIdNameDto
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
        return organizationRepository.findByIdAndByDeletedFalse(id)
                .orElseThrow(OrganizationNotFound::new);
    }

    @Override
    public List<OrganizationDto> getOrganizations(String query) {
        if (query != null && !query.isEmpty()) {
            return organizationRepository.findByNameStartingWithAndDeletedFalse(query)
                    .stream()
                    .map(organizationMapper::toDto)
                    .toList();
        }
        return organizationRepository.findAllByDeletedFalse()
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
        if (!StringUtils.hasText(dto.getName()) || !StringUtils.hasText(dto.getDescription())) {
            throw new IllegalArgumentException("Имя и описание требуется заполнить");
        }

        dto.setCreator(userService.getAuthUser());
        dto.setStatus(ContentStatus.APPROVED);

        if (avatarFile != null && !avatarFile.isEmpty()) {
            FileDto avatar = fileService.uploadAvatar(avatarFile);
            dto.setAvatar(avatar);
        }

        Organization organization = organizationMapper.toEntity(dto);
        organizationRepository.save(organization);
        return organizationMapper.toDto(organization);
    }

    @Override
    public Page<OrganizationDto> getOrganizations(String query, Pageable pageable) {
        Page<Organization> page;
        if (query != null && !query.isEmpty()) {
            page = organizationRepository.findByNameStartingWithAndDeletedFalse(query, pageable);
        } else {
            page = organizationRepository.findAllByDeletedFalse(pageable);
        }
        return page.map(organizationMapper::toDto);
    }

    @Transactional
    @Override
    public OrganizationDto update(Long id, OrganizationDto dto, MultipartFile avatarFile) {
        if (!StringUtils.hasText(dto.getName()) || !StringUtils.hasText(dto.getDescription())) {
            throw new IllegalArgumentException("Name and description must not be empty");
        }

        Organization organization = getEntityById(id);
        organization.setName(dto.getName());
        organization.setDescription(dto.getDescription());
        organization.setUrl(dto.getUrl());

        if (avatarFile != null && !avatarFile.isEmpty()) {
            File avatar = fileService.uploadAvatarReturnEntity(avatarFile);
            organization.setAvatar(avatar);
        }

        organizationRepository.save(organization);
        return organizationMapper.toDto(organization);
    }


    @Transactional
    @Override
    public boolean delete(Long id) {
        Organization organization = getEntityById(id);
        if (!organization.getEvents().isEmpty() || !organization.getOlympiadOrganizations().isEmpty()) {
            return false;
        }
        organizationRepository.deleteByIdAndSetDeleted(id);
        return true;
    }
}

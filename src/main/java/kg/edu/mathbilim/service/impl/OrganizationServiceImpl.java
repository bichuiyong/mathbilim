package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.mapper.OrganizationMapper;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    private final FileService fileService;
    private final UserService userService;

    @Transactional
    @Override
    public OrganizationDto create(OrganizationDto dto, MultipartFile avatarFile) {
        dto.setCreator(userService.getAuthUser());
        dto.setStatus(ContentStatus.PENDING_REVIEW);

        Organization organization = organizationMapper.toEntity(dto);

        if (avatarFile != null) {
            String path = fileService.uploadAvatar(avatarFile, organization.getCreator());
            organization.setAvatar(path);
        }

        organizationRepository.save(organization);
        return organizationMapper.toDto(organization);
    }
}

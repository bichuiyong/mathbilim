package kg.edu.mathbilim.service.impl.olympiad;

import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.olympiad.*;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.olympiad.OlympiadRepository;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadContactService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import kg.edu.mathbilim.service.interfaces.organization.OlympOrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;



@Service
@RequiredArgsConstructor
public class OlympiadServiceImpl implements OlympiadService {
    private final OlympiadRepository olympiadRepository;
    private final UserService userService;
    private final OlympiadContactService contactService;
    private final OlympiadStageService stageService;
    private final OlympiadContactService olympiadContactService;
    private final OlympiadStageService olympiadStageService;
    private final OlympOrganizationService olympOrganizationService;
    private final OrganizationService organizationService;

    @Override
    public Page<OlympListDto> getAll(Pageable pageable) {
        return olympiadRepository.findAll(pageable)
                .map(olympiad -> new OlympListDto(
                        olympiad.getId(),
                        olympiad.getTitle()
                ));
    }

    @Override
    public OlympiadDto getById(long id) {
        Olympiad olympiad = olympiadRepository.findById(id).orElseThrow(() -> new BlogNotFoundException("Olympiad not found"));

        List<Long> organizations = olympOrganizationService.getOrganizationIds((int) id);

        List<OrganizationDto> organizationDtos = organizationService.getByIds(organizations);

        List<OlympContactDto> contacts = olympiadContactService.getContactsByOlympId((int) id)
                .stream()
                .map(olympiadContact -> OlympContactDto
                        .builder()
                        .contactType(olympiadContact.getContactType().getName())
                        .info(olympiadContact.getInfo())
                        .build())
                .toList();

        return OlympiadDto.builder()
                .id(olympiad.getId())
                .title(olympiad.getTitle())
                .info(olympiad.getInfo())
                .rules(olympiad.getRules())
                .startDate(olympiad.getStartDate())
                .endDate(olympiad.getEndDate())
                .createdAt(olympiad.getCreatedAt())
                .updatedAt(olympiad.getUpdatedAt())
                .fileId(olympiad.getImage().getId())
                .organizations(organizationDtos)
                .contacts(contacts)
                .stages(olympiadStageService.getOlympStageDtos((int) id))
                .build();
    }
}

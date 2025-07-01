package kg.edu.mathbilim.service.impl.olympiad;

import jakarta.persistence.EntityNotFoundException;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.olympiad.*;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.model.ContactType;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadContact;
import kg.edu.mathbilim.model.olympiad.OlympiadContactId;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import kg.edu.mathbilim.model.organization.OlympiadOrganizationKey;
import kg.edu.mathbilim.repository.olympiad.OlympiadRepository;
import kg.edu.mathbilim.service.interfaces.ContactTypeService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadContactService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import kg.edu.mathbilim.service.interfaces.organization.OlympOrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Service
@Slf4j
@RequiredArgsConstructor
public class OlympiadServiceImpl implements OlympiadService {
    private final OlympiadRepository olympiadRepository;
    private final UserService userService;
    private final OlympiadContactService olympiadContactService;
    private final OlympiadStageService olympiadStageService;
    private final OlympOrganizationService olympOrganizationService;
    private final OrganizationService organizationService;
    private final FileService fileService;
    private final ContactTypeService contactTypeService;

    @Transactional
    @Override
    public void olympiadCreate(OlympiadCreateDto dto) {
        FileDto file = fileService.uploadFile(dto.getImageFile(),"general");
        File olympFile = fileService.getEntityById(file.getId());
        List<OlympiadOrganization> organizations = new ArrayList<>();
        List<OlympiadContact> contacts = new ArrayList<>();
        List<OlympiadStage> stages = new ArrayList<>();

        Olympiad olympiad = Olympiad.builder()
                .creator(userService.getEntityById(dto.getCreatorId()))
                .image(olympFile)
                .info(dto.getInfo())
                .rules(dto.getRules())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .title(dto.getTitle())
                .build();
        olympiadRepository.saveAndFlush(olympiad);

        for(Long id : dto.getOrganizationIds()) {
            Organization organization = organizationService.getByIdModel(id);
            OlympiadOrganization olympiadOrganization = OlympiadOrganization
                    .builder()
                    .olympiad(olympiad)
                    .organization(organization)
                    .id(new OlympiadOrganizationKey(olympiad.getId().longValue(),organization.getId()))
                    .build();
            organizations.add(olympiadOrganization);
        }

        for (OlympiadContactDto contact : dto.getContacts()) {
            if (contact.getInfo() == null || contact.getInfo().isBlank()) {
                continue;
            }
            ContactType contactType = contactTypeService.getById(contact.getContactType());

            OlympiadContactId contactId = new OlympiadContactId();
            contactId.setOlympiadId(olympiad.getId());
            contactId.setContactTypeId(contactType.getId());

            OlympiadContact olympiadContact = OlympiadContact
                    .builder()
                    .id(contactId)
                    .contactType(contactType)
                    .olympiad(olympiad)
                    .info(contact.getInfo())
                    .build();
            contacts.add(olympiadContact);
        }
        olympiadContactService.addAllContacts(contacts);

        for (OlympiadStageCreateDto stage : dto.getStages()) {
            OlympiadStage olympiadStage = OlympiadStage
                    .builder()
                    .olympiad(olympiad)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .startDate(stage.getEventStartDate())
                    .endDate(stage.getEventEndDate())
                    .registrationStart(stage.getRegistrationStart())
                    .registrationEnd(stage.getRegistrationEnd())
                    .stageOrder(stage.getStageOrder())
                    .result(null)
                    .build();
            stages.add(olympiadStage);
        }
        olympiadStageService.addAll(stages);

        olympiad.setOlympiadOrganizations(organizations);
        olympiad.setContactInfos(contacts);
        olympiad.setStages(stages);

        olympiadRepository.save(olympiad);
    }

    @Override
    public Page<OlympListDto> getAll(Pageable pageable) {
        return olympiadRepository.findAll(pageable)
                .map(olympiad -> new OlympListDto(
                        Math.toIntExact(olympiad.getId()),
                        olympiad.getTitle(),
                        olympiad.getImage().getId()
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
                .id(Math.toIntExact(olympiad.getId()))
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

package kg.edu.mathbilim.service.impl.olympiad;

import jakarta.persistence.EntityManager;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.olympiad.*;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.exception.nsee.PostNotFoundException;
import kg.edu.mathbilim.model.ContactType;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.model.olympiad.*;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import kg.edu.mathbilim.model.organization.OlympiadOrganizationKey;
import kg.edu.mathbilim.repository.olympiad.OlympiadApprovedListRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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
    private final EntityManager entityManager;
    private final OlympiadApprovedListRepository olympiadApprovedListRepository;

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
                    .id(new OlympiadOrganizationKey(olympiad.getId(),organization.getId()))
                    .build();
            organizations.add(olympiadOrganization);
        }

        for (OlympiadContactDto contact : dto.getContacts()) {
            if (contact.getInfo() == null || contact.getInfo().isBlank()) {
                continue;
            }
            ContactType contactType = contactTypeService.getById(Math.toIntExact(contact.getContactType()));

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

    @Transactional
    @Override
    public void olympiadUpdate(OlympiadCreateDto dto) {
        Olympiad olympiad = olympiadRepository.findById(dto.getId())
                .orElseThrow(() -> new BlogNotFoundException("Olympiad not found"));

        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            FileDto fileDto = fileService.updateFile(olympiad.getImage().getId(), dto.getImageFile());
            olympiad.setImage(File.builder()
                    .id(fileDto.getId())
                    .filename(fileDto.getFilename())
                    .filePath(fileDto.getFilePath())
                    .type(fileDto.getType())
                    .size(fileDto.getSize())
                    .s3Link(fileDto.getS3Link())
                    .build());
        }

        List<Long> organizationIds = olympiad.getOlympiadOrganizations().stream()
                .map(organ -> organ.getOrganization().getId())
                .toList();
        boolean organizationsIsSame = new HashSet<>(organizationIds).equals(new HashSet<>(dto.getOrganizationIds()));

        if (!organizationsIsSame) {
            olympiad.getOlympiadOrganizations().clear();
            olympOrganizationService.deleteByOlympiadId(olympiad.getId());

            for (Long id : dto.getOrganizationIds()) {
                Organization organization = organizationService.getByIdModel(id);
                OlympiadOrganization olympiadOrganization = OlympiadOrganization.builder()
                        .olympiad(olympiad)
                        .organization(organization)
                        .id(new OlympiadOrganizationKey(olympiad.getId(), organization.getId()))
                        .build();
                olympiad.getOlympiadOrganizations().add(olympiadOrganization);
            }
        }

        olympiadContactService.deleteByOlympiadId(olympiad.getId());
        entityManager.flush();
        entityManager.clear();
        olympiad = olympiadRepository.findById(dto.getId())
                .orElseThrow(() -> new BlogNotFoundException("Olympiad not found"));

        for (OlympiadContactDto contact : dto.getContacts()) {
            if (contact.getInfo() == null || contact.getInfo().isBlank()) {
                continue;
            }
            ContactType contactType = contactTypeService.getById(Math.toIntExact(contact.getContactType()));

            OlympiadContactId contactId = new OlympiadContactId();
            contactId.setOlympiadId(olympiad.getId());
            contactId.setContactTypeId(contactType.getId());

            OlympiadContact olympiadContact = OlympiadContact.builder()
                    .id(contactId)
                    .contactType(contactType)
                    .olympiad(olympiad)
                    .info(contact.getInfo())
                    .build();
            olympiad.getContactInfos().add(olympiadContact);
        }

        for (OlympiadStageCreateDto dtoStage : dto.getStages()) {
            for (OlympiadStage existingStage : olympiad.getStages()) {
                if (existingStage.getId().equals(dtoStage.getId())) {
                    existingStage.setUpdatedAt(LocalDateTime.now());
                    existingStage.setRegistrationEnd(dtoStage.getRegistrationEnd());
                    existingStage.setRegistrationStart(dtoStage.getRegistrationStart());
                    existingStage.setStartDate(dtoStage.getEventStartDate());
                    existingStage.setEndDate(dtoStage.getEventEndDate());
                    break;
                }
            }
        }
        olympiadStageService.saveAll(olympiad.getStages());

        olympiad.setUpdatedAt(LocalDateTime.now());
        olympiad.setStartDate(dto.getStartDate());
        olympiad.setEndDate(dto.getEndDate());
        olympiad.setTitle(dto.getTitle());
        olympiad.setInfo(dto.getInfo());
        olympiad.setRules(dto.getRules());

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
    public OlympiadCreateDto getOlympiadCreateDto(Long olympId) {
        Olympiad olympiad = olympiadRepository.findById(olympId).orElseThrow(() -> new PostNotFoundException("not found"));
        boolean hasStarted = !olympiad.getStartDate().isAfter(LocalDate.now());
        boolean hasEnded = !olympiad.getEndDate().isAfter(LocalDate.now());

        List<OlympiadStageCreateDto> stageCreateDto = olympiad.getStages().stream()
                .map(stage -> OlympiadStageCreateDto.builder()
                        .id(stage.getId())
                        .stageOrder(stage.getStageOrder())
                        .eventEndDate(stage.getEndDate())
                        .registrationStart(stage.getRegistrationStart())
                        .registrationEnd(stage.getRegistrationEnd())
                        .eventStartDate(stage.getStartDate())
                        .hasStarted(!stage.getStartDate().isAfter(LocalDate.now()))
                        .hasEnded(!stage.getEndDate().isAfter(LocalDate.now()))
                        .build()
                )
                .toList();


        List<OlympiadContactDto> contacts = olympiad.getContactInfos().stream()
                .map(contact -> OlympiadContactDto
                        .builder()
                        .contactType((long) Math.toIntExact(contact.getContactType().getId()))
                        .info(contact.getInfo())
                        .build()).toList();

        List<Long> organizationIds = olympiad.getOlympiadOrganizations().stream()
                .map(organ -> organ.getOrganization().getId())
                .toList();

        return OlympiadCreateDto
                .builder()
                .id(olympId)
                .creatorId(olympiad.getCreator().getId())
                .title(olympiad.getTitle())
                .info(olympiad.getInfo())
                .endDate(olympiad.getEndDate())
                .startDate(olympiad.getStartDate())
                .rules(olympiad.getRules())
                .imageFile(null)
                .stages(stageCreateDto)
                .organizationIds(organizationIds)
                .contacts(contacts)
                .hasStarted(hasStarted)
                .hasEnded(hasEnded)
                .build();
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
                .stages(olympiadStageService.getOlympStageDtos(id))
                .build();
    }

    @Override
    public String uploadRegistrationResult(MultipartFile uploadFile, long stageId) {
        OlympiadStage olympStage = olympiadStageService.getOlympiadStageById((int) stageId);
        File file = fileService.uploadFileReturnEntity(uploadFile, "general");
        OlympiadApprovedList olympiadApprovedList = olympiadApprovedListRepository.findByOlympiadStage(olympStage)
                .map(r -> {
                    r.setFile(file);
                    r.setUpdatedAt(LocalDateTime.now());
                    return r;
                })
                .orElseGet(() -> OlympiadApprovedList.builder()
                        .file(file)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .olympiadStage(olympStage)
                        .build()
                );
        olympiadApprovedListRepository.save(olympiadApprovedList);
        olympiadStageService.updateTime(olympStage);
        return "redirect:/olympiad/details?id="+olympStage.getOlympiad().getId();
    }

}

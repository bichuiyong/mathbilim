package kg.edu.mathbilim.service.impl.olympiad;

import jakarta.persistence.EntityManager;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.olympiad.*;
import kg.edu.mathbilim.enums.FileType;
import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.exception.nsee.PostNotFoundException;
import kg.edu.mathbilim.model.ContactType;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadApprovedList;
import kg.edu.mathbilim.model.olympiad.OlympiadContact;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.olympiad.OlympiadApprovedListRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadRepository;
import kg.edu.mathbilim.service.interfaces.ContactTypeService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadContactService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadStageService;
import kg.edu.mathbilim.service.interfaces.organization.OlympOrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OlympiadServiceImplTest {

    @Mock
    private OlympiadRepository olympiadRepository;
    @Mock
    private UserService userService;
    @Mock
    private OlympiadContactService olympiadContactService;
    @Mock
    private OlympiadStageService olympiadStageService;
    @Mock
    private OlympOrganizationService olympOrganizationService;
    @Mock
    private OrganizationService organizationService;
    @Mock
    private FileService fileService;
    @Mock
    private ContactTypeService contactTypeService;
    @Mock
    private EntityManager entityManager;
    @Mock
    private OlympiadApprovedListRepository olympiadApprovedListRepository;

    @InjectMocks
    private OlympiadServiceImpl olympiadService;

        private OlympiadCreateDto createDto;
        private Olympiad olympiad;
        private File imageFile;
        private User creator;
        private Organization organization;
        private ContactType contactType;

        @BeforeEach
        void setUp() {
            setupTestData();
        }

        private void setupTestData() {
            imageFile = File.builder()
                    .id(1L)
                    .filename("test-image.jpg")
                    .filePath("/path/to/image")
                    .type(FileType.JPEG)
                    .size(1000L)
                    .s3Link("https://s3.link/image.jpg")
                    .build();

            creator = User.builder()
                    .id(1L)
                    .build();

            organization = Organization.builder()
                    .id(1L)
                    .name("Test Organization")
                    .build();

            contactType = ContactType.builder()
                    .id(1L)
                    .name("Email")
                    .build();

            List<OlympiadContactDto> contacts = new ArrayList<>(Arrays.asList(
                    OlympiadContactDto.builder().contactType(1L).info("test@example.com").build(),
                    OlympiadContactDto.builder().contactType(1L).info("https://website.com").build(),
                    OlympiadContactDto.builder().contactType(1L).info("").build()
            ));


            List<OlympiadStageCreateDto> stages = Collections.singletonList(
                    OlympiadStageCreateDto.builder()
                            .id(1)
                            .stageOrder(1)
                            .eventStartDate(LocalDate.now().plusDays(1))
                            .eventEndDate(LocalDate.now().plusDays(5))
                            .registrationStart(LocalDate.now())
                            .registrationEnd(LocalDate.now().plusDays(2))
                            .build()
            );

            createDto = OlympiadCreateDto.builder()
                    .id(1L)
                    .creatorId(1L)
                    .title("Test Olympiad")
                    .info("Test info")
                    .rules("Test rules")
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusDays(10))
                    .organizationIds(List.of(1L))
                    .contacts(contacts)
                    .stages(stages)
                    .build();

            olympiad = Olympiad.builder()
                    .id(1L)
                    .creator(creator)
                    .image(imageFile)
                    .title("Test Olympiad")
                    .info("Test info")
                    .rules("Test rules")
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusDays(10))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .olympiadOrganizations(new ArrayList<>())
                    .contactInfos(new ArrayList<>())
                    .stages(new ArrayList<>())
                    .build();
        }

        @Test
        void olympiadCreate_Success() {
            MultipartFile mockFile = mock(MultipartFile.class);
            createDto.setImageFile(mockFile);

            FileDto fileDto = FileDto.builder().id(1L).build();

            when(fileService.uploadFile(mockFile, "general")).thenReturn(fileDto);
            when(fileService.getEntityById(1L)).thenReturn(imageFile);
            when(userService.getEntityById(1L)).thenReturn(creator);
            when(olympiadRepository.saveAndFlush(any(Olympiad.class))).thenReturn(olympiad);
            when(organizationService.getByIdModel(1L)).thenReturn(organization);
            when(contactTypeService.getById(1)).thenReturn(contactType);

            olympiadService.olympiadCreate(createDto);

            verify(fileService).uploadFile(mockFile, "general");
            verify(olympiadRepository).saveAndFlush(any(Olympiad.class));
            verify(olympiadContactService).addAllContacts(anyList());
            verify(olympiadStageService).addAll(anyList());
            verify(olympiadRepository).save(any(Olympiad.class));
        }

        @Test
        void olympiadCreate_SkipsEmptyContacts() {
            MultipartFile mockFile = mock(MultipartFile.class);
            createDto.setImageFile(mockFile);

            FileDto fileDto = FileDto.builder().id(1L).build();

            when(fileService.uploadFile(mockFile, "general")).thenReturn(fileDto);
            when(fileService.getEntityById(1L)).thenReturn(imageFile);
            when(userService.getEntityById(1L)).thenReturn(creator);
            when(olympiadRepository.saveAndFlush(any(Olympiad.class))).thenReturn(olympiad);
            when(organizationService.getByIdModel(1L)).thenReturn(organization);
            when(contactTypeService.getById(1)).thenReturn(contactType);

            olympiadService.olympiadCreate(createDto);

            verify(olympiadContactService).addAllContacts(argThat(contacts ->
                    contacts.size() == 2  //в тестовых контактах один параметр пустой
            ));
        }

        @Test
        void olympiadCreate_RemovesHttpsPrefix() {
            MultipartFile mockFile = mock(MultipartFile.class);
            createDto.setImageFile(mockFile);

            FileDto fileDto = FileDto.builder().id(1L).build();

            when(fileService.uploadFile(mockFile, "general")).thenReturn(fileDto);
            when(fileService.getEntityById(1L)).thenReturn(imageFile);
            when(userService.getEntityById(1L)).thenReturn(creator);
            when(olympiadRepository.saveAndFlush(any(Olympiad.class))).thenReturn(olympiad);
            when(organizationService.getByIdModel(1L)).thenReturn(organization);
            when(contactTypeService.getById(1)).thenReturn(contactType);

            olympiadService.olympiadCreate(createDto);

            verify(olympiadContactService).addAllContacts(argThat(contacts -> {
                Optional<OlympiadContact> websiteContact = contacts.stream()
                        .filter(c -> c.getInfo().equals("website.com"))
                        .findFirst();
                return websiteContact.isPresent();
            }));
        }

        @Test
        void olympiadUpdate_Success() {
            MultipartFile mockFile = mock(MultipartFile.class);
            createDto.setImageFile(mockFile);

            List<OlympiadOrganization> existingOrganizations = Collections.singletonList(
                    OlympiadOrganization.builder()
                            .organization(organization)
                            .build()
            );
            olympiad.setOlympiadOrganizations(existingOrganizations);

            List<OlympiadStage> existingStages = Collections.singletonList(
                    OlympiadStage.builder()
                            .id(1)
                            .stageOrder(1)
                            .startDate(LocalDate.now())
                            .endDate(LocalDate.now().plusDays(5))
                            .registrationStart(LocalDate.now())
                            .registrationEnd(LocalDate.now().plusDays(2))
                            .build()
            );
            olympiad.setStages(existingStages);

            when(olympiadRepository.findById(1L))
                    .thenReturn(Optional.of(olympiad))
                    .thenReturn(Optional.of(olympiad));
            when(fileService.updateFile(eq(1L), eq(mockFile)))
                    .thenReturn(FileDto.builder()
                            .id(1L)
                            .filename("updated-image.jpg")
                            .filePath("/path/to/updated-image")
                            .type(FileType.JPEG)
                            .size(1500L)
                            .s3Link("https://s3.link/updated-image.jpg")
                            .build());
            lenient().when(organizationService.getByIdModel(1L)).thenReturn(organization);
            when(contactTypeService.getById(1)).thenReturn(contactType);

            olympiadService.olympiadUpdate(createDto);

            verify(fileService).updateFile(1L, mockFile);
            verify(olympiadContactService).deleteByOlympiadId(1L);
            verify(olympiadStageService).saveAll(anyList());
            verify(olympiadRepository).save(any(Olympiad.class));
        }

        @Test
        void olympiadUpdate_OlympiadNotFound() {
            when(olympiadRepository.findById(1L)).thenReturn(Optional.empty());
            assertThrows(BlogNotFoundException.class, () -> {
                olympiadService.olympiadUpdate(createDto);
            });
        }

        @Test
        void olympiadUpdate_OrganizationsChanged() {
            createDto.setOrganizationIds(Arrays.asList(1L, 2L));

            List<OlympiadOrganization> existingOrganizations = new ArrayList<>(Collections.singletonList(
                    OlympiadOrganization.builder()
                            .organization(Organization.builder().id(3L).build())
                            .build()
            ));
            olympiad.setOlympiadOrganizations(existingOrganizations);
            olympiad.setStages(new ArrayList<>());

            Organization newOrganization = Organization.builder().id(2L).build();

            when(olympiadRepository.findById(1L))
                    .thenReturn(Optional.of(olympiad))
                    .thenReturn(Optional.of(olympiad));
            when(organizationService.getByIdModel(1L)).thenReturn(organization);
            when(organizationService.getByIdModel(2L)).thenReturn(newOrganization);
            when(contactTypeService.getById(1)).thenReturn(contactType);

            olympiadService.olympiadUpdate(createDto);

            verify(olympOrganizationService).deleteByOlympiadId(1L);
            assertEquals(2, olympiad.getOlympiadOrganizations().size());
        }

        @Test
        void getAll_Success() {
            Pageable pageable = PageRequest.of(0, 10);
            List<Olympiad> olympiads = Arrays.asList(olympiad);
            Page<Olympiad> olympiadPage = new PageImpl<>(olympiads, pageable, 1);

            when(olympiadRepository.findAll(pageable)).thenReturn(olympiadPage);

            Page<OlympListDto> result = olympiadService.getAll(pageable);

            assertEquals(1, result.getContent().size());
            OlympListDto dto = result.getContent().get(0);
            assertEquals(1, dto.getId());
            assertEquals("Test Olympiad", dto.getTitle());
            assertEquals(1L, dto.getFileId());
        }

        @Test
        void getOlympiadCreateDto_Success() {
            setupOlympiadWithRelations();
            when(olympiadRepository.findById(1L)).thenReturn(Optional.of(olympiad));

            OlympiadCreateDto result = olympiadService.getOlympiadCreateDto(1L);

            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals("Test Olympiad", result.getTitle());
            assertEquals(1, result.getStages().size());
            assertEquals(1, result.getContacts().size());
            assertEquals(1, result.getOrganizationIds().size());
            assertTrue(result.isHasStarted());
            assertFalse(result.isHasEnded());
        }

        @Test
        void getOlympiadCreateDto_NotFound() {
            when(olympiadRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(PostNotFoundException.class, () -> {
                olympiadService.getOlympiadCreateDto(1L);
            });
        }

    @Test
    void getById_Success() {
        setupOlympiadWithRelations();

        List<Long> organizationIds = Arrays.asList(1L);
        List<OrganizationDto> organizationDtos = Arrays.asList(
                OrganizationDto.builder().id(1L).name("Test Organization").build()
        );
        List<OlympContactDto> olympContactDtos = Arrays.asList(
                OlympContactDto.builder()
                        .contactType("Email")
                        .info("test@example.com")
                        .build()
        );
        List<OlympiadStageDto> stageDtos = Arrays.asList(
                OlympiadStageDto.builder().id(1L).stageOrder(1).build()
        );

        when(olympiadRepository.findById(1L)).thenReturn(Optional.of(olympiad));
        when(olympOrganizationService.getOrganizationIds(1)).thenReturn(organizationIds);
        when(organizationService.getByIds(organizationIds)).thenReturn(organizationDtos);
        when(olympiadContactService.getContactsByOlympId(1)).thenReturn(Arrays.asList(
                OlympiadContact.builder()
                        .contactType(contactType)
                        .info("test@example.com")
                        .build()
        ));
        when(olympiadStageService.getOlympStageDtos(1L)).thenReturn(stageDtos);

        OlympiadDto result = olympiadService.getById(1L);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Olympiad", result.getTitle());
        assertEquals(1, result.getOrganizations().size());
        assertEquals(1, result.getContacts().size());
        assertEquals(1, result.getStages().size());
    }

        @Test
        void getById_NotFound() {
            when(olympiadRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(BlogNotFoundException.class, () -> {
                olympiadService.getById(1L);
            });
        }

    @Test
    void uploadRegistrationResult_NewApprovedList() {
        MultipartFile mockFile = mock(MultipartFile.class);
        OlympiadStage stage = OlympiadStage.builder()
                .id(1)
                .olympiad(olympiad)
                .build();

        when(olympiadStageService.getOlympiadStageById(1)).thenReturn(stage);
        when(fileService.uploadFileReturnEntity(mockFile, "general")).thenReturn(imageFile);
        when(olympiadApprovedListRepository.findByOlympiadStage(stage))
                .thenReturn(Optional.empty());

        String result = olympiadService.uploadRegistrationResult(mockFile, 1L);

        assertEquals("redirect:/olympiad/details?id=1", result);
        verify(olympiadApprovedListRepository).save(any(OlympiadApprovedList.class));
        verify(olympiadStageService).updateTime(stage);
    }

        @Test
        void uploadRegistrationResult_UpdateExistingApprovedList() {
            MultipartFile mockFile = mock(MultipartFile.class);
            OlympiadStage stage = OlympiadStage.builder()
                    .id(1)
                    .olympiad(olympiad)
                    .build();

            OlympiadApprovedList existingApprovedList = OlympiadApprovedList.builder()
                    .id(1L)
                    .file(imageFile)
                    .olympiadStage(stage)
                    .createdAt(LocalDateTime.now().minusDays(1))
                    .updatedAt(LocalDateTime.now().minusDays(1))
                    .build();

            when(olympiadStageService.getOlympiadStageById(1)).thenReturn(stage);
            when(fileService.uploadFileReturnEntity(mockFile, "general")).thenReturn(imageFile);
            when(olympiadApprovedListRepository.findByOlympiadStage(stage))
                    .thenReturn(Optional.of(existingApprovedList));

            String result = olympiadService.uploadRegistrationResult(mockFile, 1L);

            assertEquals("redirect:/olympiad/details?id=1", result);
            verify(olympiadApprovedListRepository).save(argThat(approvedList ->
                    approvedList.getFile().equals(imageFile) &&
                            approvedList.getUpdatedAt().isAfter(LocalDateTime.now().minusMinutes(1))
            ));
            verify(olympiadStageService).updateTime(stage);
        }

        private void setupOlympiadWithRelations() {
            OlympiadStage stage = OlympiadStage.builder()
                    .id(1)
                    .stageOrder(1)
                    .startDate(LocalDate.now().minusDays(1)) // Started
                    .endDate(LocalDate.now().plusDays(5))   // Not ended
                    .registrationStart(LocalDate.now().minusDays(2))
                    .registrationEnd(LocalDate.now().plusDays(1))
                    .build();
            olympiad.setStages(Arrays.asList(stage));

            OlympiadContact contact = OlympiadContact.builder()
                    .contactType(contactType)
                    .info("test@example.com")
                    .build();
            olympiad.setContactInfos(Arrays.asList(contact));

            OlympiadOrganization olympiadOrganization = OlympiadOrganization.builder()
                    .organization(organization)
                    .build();
            olympiad.setOlympiadOrganizations(Arrays.asList(olympiadOrganization));

            olympiad.setStartDate(LocalDate.now().minusDays(1));
            olympiad.setEndDate(LocalDate.now().plusDays(5));
        }

}


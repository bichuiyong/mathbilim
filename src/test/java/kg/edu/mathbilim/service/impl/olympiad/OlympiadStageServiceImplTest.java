package kg.edu.mathbilim.service.impl.olympiad;

import jakarta.ws.rs.NotFoundException;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadStageCreateDto;
import kg.edu.mathbilim.dto.olympiad.OlympiadStageDto;
import kg.edu.mathbilim.dto.olympiad.RegistrationDto;
import kg.edu.mathbilim.enums.FileType;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Result;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadApprovedList;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.model.olympiad.Registration;
import kg.edu.mathbilim.model.reference.Role;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.ResultRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadApprovedListRepository;
import kg.edu.mathbilim.repository.olympiad.OlympiadStageRepository;
import kg.edu.mathbilim.repository.olympiad.RegistrationRepository;
import kg.edu.mathbilim.service.interfaces.UserService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OlympiadStageServiceImplTest {

    @Mock
    private OlympiadStageRepository repository;

    @Mock
    private OlympiadApprovedListRepository olympiadApprovedListRepository;

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private UserService userService;

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private OlympiadStageServiceImpl olympiadStageService;

    private Olympiad olympiad;
    private OlympiadStage stage1;
    private OlympiadStage stage2;
    private User user;
    private Role userRole;
    private Role adminRole;
    private Registration registration;
    private RegistrationDto registrationDto;
    private File file;
    private Result result;
    private OlympiadApprovedList approvedList;

    @BeforeEach
    void setUp() {
        setupTestData();
    }

    private void setupTestData() {
        file = File.builder()
                .id(1L)
                .filename("test-file.pdf")
                .filePath("/path/to/file")
                .type(FileType.PDF)
                .size(1000L)
                .s3Link("https://s3.link/file.pdf")
                .build();

        userRole = Role.builder()
                .id(1)
                .name("USER")
                .build();

        adminRole = Role.builder()
                .id(2)
                .name("ADMIN")
                .build();

        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(userRole)
                .build();

        olympiad = Olympiad.builder()
                .id(1L)
                .title("Test Olympiad")
                .build();

        result = Result.builder()
                .id(1L)
                .file(file)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        approvedList = OlympiadApprovedList.builder()
                .id(1L)
                .file(file)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        stage1 = OlympiadStage.builder()
                .id(1)
                .olympiad(olympiad)
                .stageOrder(1)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .registrationStart(LocalDate.now().minusDays(1))
                .registrationEnd(LocalDate.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .result(Collections.singletonList(result))
                .approvedList(Collections.singletonList(approvedList))
                .build();

        stage2 = OlympiadStage.builder()
                .id(2)
                .olympiad(olympiad)
                .stageOrder(2)
                .startDate(LocalDate.now().plusDays(6))
                .endDate(LocalDate.now().plusDays(10))
                .registrationStart(LocalDate.now().plusDays(2))
                .registrationEnd(LocalDate.now().plusDays(4))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .result(Collections.emptyList())
                .approvedList(Collections.emptyList())
                .build();

        registration = Registration.builder()
                .id(1L)
                .user(user)
                .olympiadStage(stage1)
                .fullName("John Doe")
                .email("john@example.com")
                .phoneNumber("+1234567890")
                .classNumber("10")
                .school("Test School")
                .region("Test Region")
                .district("Test District")
                .locality("Test Locality")
                .parentFullName("Jane Doe")
                .parentEmail("jane@example.com")
                .parentPhoneNumber("+0987654321")
                .classTeacherFullName("Teacher Name")
                .telegram("@johndoe")
                .created(LocalDateTime.now())
                .build();

        registrationDto = RegistrationDto.builder()
                .fullName("John Doe")
                .email("john@example.com")
                .phoneNumber("+1234567890")
                .classNumber("10")
                .school("Test School")
                .region("Test Region")
                .district("Test District")
                .locality("Test Locality")
                .parentFullName("Jane Doe")
                .parentEmail("jane@example.com")
                .parentPhoneNumber("+0987654321")
                .classTeacherFullName("Teacher Name")
                .telegram("@johndoe")
                .build();
    }

    @Test
    void save_WithValidDto_SavesStages() {
        List<OlympiadStageCreateDto> stageCreateDtos = Arrays.asList(
                OlympiadStageCreateDto.builder()
                        .eventStartDate(LocalDate.now().plusDays(1))
                        .eventEndDate(LocalDate.now().plusDays(5))
                        .registrationStart(LocalDate.now())
                        .registrationEnd(LocalDate.now().plusDays(2))
                        .build(),
                OlympiadStageCreateDto.builder()
                        .eventStartDate(LocalDate.now().plusDays(6))
                        .eventEndDate(LocalDate.now().plusDays(10))
                        .registrationStart(LocalDate.now().plusDays(3))
                        .registrationEnd(LocalDate.now().plusDays(5))
                        .build()
        );

        OlympiadCreateDto dto = OlympiadCreateDto.builder()
                .stages(stageCreateDtos)
                .build();

        when(repository.saveAndFlush(any(OlympiadStage.class))).thenReturn(stage1);

        olympiadStageService.save(dto, olympiad);

        verify(repository, times(2)).saveAndFlush(any(OlympiadStage.class));
        verify(repository).saveAndFlush(argThat(stage ->
                stage.getStageOrder() == 1 && stage.getOlympiad().equals(olympiad)
        ));
        verify(repository).saveAndFlush(argThat(stage ->
                stage.getStageOrder() == 2 && stage.getOlympiad().equals(olympiad)
        ));
    }

    @Test
    void save_WithNullStages_DoesNothing() {
        OlympiadCreateDto dto = OlympiadCreateDto.builder()
                .stages(null)
                .build();

        olympiadStageService.save(dto, olympiad);

        verifyNoInteractions(repository);
    }

    @Test
    void save_WithEmptyStages_DoesNothing() {
        OlympiadCreateDto dto = OlympiadCreateDto.builder()
                .stages(Collections.emptyList())
                .build();

        olympiadStageService.save(dto, olympiad);

        verifyNoInteractions(repository);
    }

    @Test
    void getOlympStageDtos_WithExistingStages_ReturnsStageList() {
        Long olympiadId = 1L;
        List<OlympiadStage> stages = Arrays.asList(stage1, stage2);

        when(repository.getOlympiadStageByOlympiad_IdOrderByStageOrderAsc(olympiadId))
                .thenReturn(stages);

        List<OlympiadStageDto> result = olympiadStageService.getOlympStageDtos(olympiadId);

        assertNotNull(result);
        assertEquals(2, result.size());

        OlympiadStageDto dto1 = result.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals(1, dto1.getStageOrder());
        assertEquals(1, dto1.getResult().size());
        assertEquals(1, dto1.getApprovedList().size());

        OlympiadStageDto dto2 = result.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals(2, dto2.getStageOrder());
        assertEquals(0, dto2.getResult().size());
        assertEquals(0, dto2.getApprovedList().size());

        verify(repository).getOlympiadStageByOlympiad_IdOrderByStageOrderAsc(olympiadId);
    }

    @Test
    void getOlympStageDtos_WithNonExistentOlympiad_ReturnsEmptyList() {
        Long olympiadId = 999L;
        when(repository.getOlympiadStageByOlympiad_IdOrderByStageOrderAsc(olympiadId))
                .thenReturn(Collections.emptyList());

        List<OlympiadStageDto> result = olympiadStageService.getOlympStageDtos(olympiadId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).getOlympiadStageByOlympiad_IdOrderByStageOrderAsc(olympiadId);
    }

    @Test
    void addAll_WithValidStages_SavesAll() {
        List<OlympiadStage> stages = Arrays.asList(stage1, stage2);
        when(repository.saveAll(stages)).thenReturn(stages);

        olympiadStageService.addAll(stages);

        verify(repository).saveAll(stages);
    }

    @Test
    void deleteByOlympiadId_WithValidId_DeletesStages() {
        Long olympiadId = 1L;

        olympiadStageService.deleteByOlympiadId(olympiadId);

        verify(repository).deleteByOlympiadId(olympiadId);
    }

    @Test
    void getOlympiadStageById_WithExistingId_ReturnsStage() {
        Integer stageId = 1;
        when(repository.findById(stageId)).thenReturn(Optional.of(stage1));

        OlympiadStage result = olympiadStageService.getOlympiadStageById(stageId);

        assertNotNull(result);
        assertEquals(stage1, result);
        verify(repository).findById(stageId);
    }

    @Test
    void getOlympiadStageById_WithNonExistentId_ThrowsNotFoundException() {
        Integer stageId = 999;
        when(repository.findById(stageId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            olympiadStageService.getOlympiadStageById(stageId);
        });
    }

    @Test
    void saveAll_WithValidStages_ReturnsUpdatedStages() {
        List<OlympiadStage> stages = Arrays.asList(stage1, stage2);
        when(repository.saveAll(stages)).thenReturn(stages);

        List<OlympiadStage> result = olympiadStageService.saveAll(stages);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository).saveAll(stages);
    }

    @Test
    void updateTime_WithValidStage_UpdatesTimestamp() throws InterruptedException {
        LocalDateTime originalTime = stage1.getUpdatedAt();

        when(repository.save(stage1)).thenReturn(stage1);
        Thread.sleep(1);
        olympiadStageService.updateTime(stage1);

        assertNotNull(stage1.getUpdatedAt(), "updatedAt не должен быть null после обновления");
        if (originalTime != null) {
            assertNotEquals(originalTime, stage1.getUpdatedAt(), "updatedAt должен измениться");
        }

        verify(repository).save(stage1);
    }


    @Test
    void createRegistrationOlympiad_WithValidData_CreatesRegistration() {
        Long stageId = 1L;
        String userName = "test@example.com";

        when(repository.findById(stageId)).thenReturn(Optional.of(stage1));
        when(userService.findByEmail(userName)).thenReturn(user);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Optional<Long> result = olympiadStageService.createRegistrationOlympiad(registrationDto,
                stageId, userName);

        assertTrue(result.isPresent());
        assertEquals(olympiad.getId(), result.get());
        verify(registrationRepository).save(any(Registration.class));
        verify(userService).findByEmail(userName);
    }

    @Test
    void createRegistrationOlympiad_WithNonExistentStage_ReturnsEmpty() {
        Long stageId = 999L;
        String userName = "test@example.com";

        when(repository.findById(stageId)).thenReturn(Optional.empty());

        Optional<Long> result = olympiadStageService.createRegistrationOlympiad(registrationDto,
                stageId, userName);

        assertFalse(result.isPresent());
        verifyNoInteractions(registrationRepository);
        verifyNoInteractions(userService);
    }

    @Test
    void checkRegisterActually_WithActiveRegistration_ReturnsTrue() {
        long stageId = 1L;
        stage1.setRegistrationStart(LocalDate.now().minusDays(1));
        stage1.setRegistrationEnd(LocalDate.now().plusDays(1));

        when(repository.findById(stageId)).thenReturn(Optional.of(stage1));

        boolean result = olympiadStageService.checkRegisterActually(stageId);

        assertTrue(result);
    }

    @Test
    void checkRegisterActually_WithExpiredRegistration_ReturnsFalse() {
        long stageId = 1L;
        stage1.setRegistrationStart(LocalDate.now().minusDays(5));
        stage1.setRegistrationEnd(LocalDate.now().minusDays(1));

        when(repository.findById(stageId)).thenReturn(Optional.of(stage1));

        boolean result = olympiadStageService.checkRegisterActually(stageId);

        assertFalse(result);
    }

    @Test
    void checkRegisterActually_WithFutureRegistration_ReturnsFalse() {
        long stageId = 1L;
        stage1.setRegistrationStart(LocalDate.now().plusDays(1));
        stage1.setRegistrationEnd(LocalDate.now().plusDays(5));

        when(repository.findById(stageId)).thenReturn(Optional.of(stage1));

        boolean result = olympiadStageService.checkRegisterActually(stageId);

        assertFalse(result);
    }

    @Test
    void checkRegisterActually_WithNonExistentStage_ReturnsFalse() {
        long stageId = 999L;
        when(repository.findById(stageId)).thenReturn(Optional.empty());

        boolean result = olympiadStageService.checkRegisterActually(stageId);

        assertFalse(result);
    }

    @Test
    void userHasRegistered_WithRegularUserAndExistingRegistration_ReturnsTrue() {
        String userName = "test@example.com";
        long stageId = 1L;

        when(userService.findByEmail(userName)).thenReturn(user);
        when(repository.findById(stageId)).thenReturn(Optional.of(stage1));
        when(registrationRepository.existsByOlympiadStageAndUser(stage1,
                user)).thenReturn(true);

        boolean result = olympiadStageService.userHasRegistered(userName, stageId);

        assertTrue(result);
    }

    @Test
    void userHasRegistered_WithAdminUser_ReturnsFalse() {
        String userName = "admin@example.com";
        long stageId = 1L;
        User adminUser = User.builder()
                .id(2L)
                .email(userName)
                .role(adminRole)
                .build();

        when(userService.findByEmail(userName)).thenReturn(adminUser);

        boolean result = olympiadStageService.userHasRegistered(userName, stageId);

        assertFalse(result);
        verifyNoInteractions(repository);
        verifyNoInteractions(registrationRepository);
    }

    @Test
    void userHasRegistered_WithModerUser_ReturnsFalse() {
        String userName = "moder@example.com";
        long stageId = 1L;
        Role moderRole = Role.builder().id(3).name("MODER").build();
        User moderUser = User.builder()
                .id(3L)
                .email(userName)
                .role(moderRole)
                .build();

        when(userService.findByEmail(userName)).thenReturn(moderUser);

        boolean result = olympiadStageService.userHasRegistered(userName, stageId);

        assertFalse(result);
    }

    @Test
    void userHasRegistered_WithRegularUserAndNoRegistration_ReturnsFalse() {
        String userName = "test@example.com";
        long stageId = 1L;

        when(userService.findByEmail(userName)).thenReturn(user);
        when(repository.findById(stageId)).thenReturn(Optional.of(stage1));
        when(registrationRepository.existsByOlympiadStageAndUser(stage1,
                user)).thenReturn(false);

        boolean result = olympiadStageService.userHasRegistered(userName, stageId);

        assertFalse(result);
    }

    @Test
    void getOlympiadRegistrations_WithValidParameters_ReturnsPagedResults() {
        Long stageId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        String keyword = "test";
        List<Registration> registrations = Arrays.asList(registration);
        Page<Registration> registrationPage = new PageImpl<>(registrations, pageable,
                1);

        when(registrationRepository.getByOlympiadStage_Id(stageId, keyword,
                pageable))
                .thenReturn(registrationPage);

        Page<RegistrationDto> result =
                olympiadStageService.getOlympiadRegistrations(stageId, pageable,
                        keyword);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        RegistrationDto dto = result.getContent().get(0);
        assertEquals("John Doe", dto.getFullName());
        assertEquals("Test Olympiad", dto.getOlympiadName());
        assertEquals("test@example.com", dto.getUserName());
    }

    @Test
    void
    getOlympiadRegistrationsForExcel_WithValidStageId_ReturnsRegistrationList()
    {
        Long stageId = 1L;
        List<Registration> registrations = Arrays.asList(registration);

        when(registrationRepository.getByOlympiadStage_IdForExcel(stageId)).thenReturn(registrations);

        List<RegistrationDto> result =
                olympiadStageService.getOlympiadRegistrationsForExcel(stageId);

        assertNotNull(result);
        assertEquals(1, result.size());
        RegistrationDto dto = result.get(0);
        assertEquals("John Doe", dto.getFullName());
        assertEquals("Test Olympiad", dto.getOlympiadName());
        assertEquals("test@example.com", dto.getUserName());
    }

    @Test
    void
    getOlympiadRegistrationsForExcel_WithNonExistentStage_ReturnsEmptyList()
    {
        Long stageId = 999L;
        when(registrationRepository.getByOlympiadStage_IdForExcel(stageId)).thenReturn(Collections.emptyList());

        List<RegistrationDto> result =
                olympiadStageService.getOlympiadRegistrationsForExcel(stageId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_WithManyStages_CreatesCorrectOrder() {
        List<OlympiadStageCreateDto> manyStages =
                IntStream.range(1, 6)
                        .mapToObj(i -> OlympiadStageCreateDto.builder()
                                .eventStartDate(LocalDate.now().plusDays(i))
                                .eventEndDate(LocalDate.now().plusDays(i + 2))
                                .registrationStart(LocalDate.now())
                                .registrationEnd(LocalDate.now().plusDays(1))
                                .build())
                        .toList();

        OlympiadCreateDto dto = OlympiadCreateDto.builder()
                .stages(manyStages)
                .build();

        when(repository.saveAndFlush(any(OlympiadStage.class))).thenReturn(stage1);

        olympiadStageService.save(dto, olympiad);

        verify(repository,
                times(5)).saveAndFlush(any(OlympiadStage.class));
        for (int i = 1; i <= 5; i++) { final int
                expectedOrder=i;
            verify(repository).saveAndFlush(argThat(stage ->
                    stage.getStageOrder() == expectedOrder
            ));
        }
    }

    @Test
    void
    userHasRegistered_WithNonExistentStage_ReturnsFalse()
    {
        String userName = "test@example.com";
        long stageId = 999L;

        when(userService.findByEmail(userName)).thenReturn(user);
        when(repository.findById(stageId)).thenReturn(Optional.empty());

        boolean result =
                olympiadStageService.userHasRegistered(userName,
                        stageId);

        assertFalse(result);
        verify(registrationRepository).existsByOlympiadStageAndUser(null,
                user);
    }
}
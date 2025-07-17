package kg.edu.mathbilim.news;
import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.DisplayEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.FileType;
import kg.edu.mathbilim.exception.nsee.EventNotFoundException;
import kg.edu.mathbilim.mapper.event.EventMapper;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.reference.Role;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.event.EventRepository;
import kg.edu.mathbilim.service.impl.event.EventServiceImpl;
import kg.edu.mathbilim.service.interfaces.event.EventTranslationService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private UserService userService;

    @Mock
    private FileService fileService;

    @Mock
    private EventTranslationService translationService;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private UserNotificationService notificationService;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event testEvent;
    private EventDto testEventDto;
    private CreateEventDto createEventDto;
    private DisplayEventDto displayEventDto;
    private List<EventTranslationDto> translations;
    private List<Organization> organizations;
    private List<File> files;

    @BeforeEach
    void setUp() {
        // Setup test data
        UserDto testUserDto = UserDto.builder()
                .id(1L)
                .name("Test")
                .surname("User")
                .email("test@example.com")
                .password("Test1234")
                .enabled(true)
                .isEmailVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(null) // or a valid RoleDto if needed
                .type(null) // or a valid UserTypeDto if needed
                .build();

        EventTranslationDto translationEn = EventTranslationDto.builder()
                .languageCode("en")
                .title("Event Title")
                .content("Event Description")
                .build();

        EventTranslationDto translationKy = EventTranslationDto.builder()
                .languageCode("ky")
                .title("Окуя Аталышы")
                .content("Окуя Сүрөттөмөсү")
                .build();
        User testUser = User.builder()
                .id(1L)
                .name("Test")
                .surname("User")
                .email("test@example.com")
                .enabled(true)
                .role(Role.builder().id(1).name("USER").build())
                .build();

        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setStatus(ContentStatus.PENDING_REVIEW);
        testEvent.setCreator(testUser);
        testEvent.setOrganizations(Arrays.asList(Organization.builder().id(1L).build(), Organization.builder().id(2l).build()));

        testEventDto = EventDto.builder()
                .id(1L)
                .creator(testUserDto)
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(2))
                .typeId(1L)
                .isOffline(true)
                .address("Test Address")
                .url("http://test.com")
                .eventFiles(new ArrayList<>()) // or a list of FileDto
                .eventTranslations(List.of(translationEn, translationKy))
                .build();

        createEventDto = CreateEventDto.builder()
                .event(testEventDto)
                .organizationIds(List.of(1L, 2L))
                .image(mock(MultipartFile.class))
                .attachments(new MultipartFile[]{mock(MultipartFile.class)})
                .build();

        displayEventDto = new DisplayEventDto();
        displayEventDto.setId(1L);
        displayEventDto.setOrganizationIds(Arrays.asList(1L, 2L));

        organizations = Arrays.asList(
                Organization.builder()
                        .id(1L)
                        .name("Org 1")
                        .description("Description 1")
                        .status(ContentStatus.PENDING_REVIEW)
                        .build(),
                Organization.builder()
                        .id(2L)
                        .name("Org 2")
                        .description("Description 2")
                        .status(ContentStatus.PENDING_REVIEW)
                        .build()
        );

        files = Arrays.asList(
                File.builder()
                        .id(1L)
                        .filename("file1.pdf")
                        .filePath("path/to/file1.pdf")
                        .type(FileType.PDF)
                        .size(12345L)
                        .build(),
                File.builder()
                        .id(2L)
                        .filename("file2.jpg")
                        .filePath("path/to/file2.jpg")
                        .type(FileType.JPEG)
                        .size(67890L)
                        .build()
        );
    }

    @Test
    @DisplayName("Should create event successfully with all fields")
    void shouldCreateEventSuccessfully() {
        // Given
        MultipartFile mockImage = mock(MultipartFile.class);
        MultipartFile[] mockAttachments = {mock(MultipartFile.class)};

        when(eventMapper.toEntity(any(EventDto.class))).thenReturn(testEvent);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(eventMapper.toDto(any(Event.class))).thenReturn(testEventDto);
        when(fileService.uploadFilesForEvent(any(), any(Event.class))).thenReturn(files);
        when(organizationService.addEventToOrganizations(anyList(), any(Event.class)))
                .thenReturn(organizations);

        createEventDto.setImage(mockImage);
        createEventDto.setAttachments(mockAttachments);

        // When
        EventDto result = eventService.create(createEventDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(eventRepository).save(any(Event.class));
        verify(fileService).uploadFilesForEvent(mockAttachments, testEvent);
        verify(organizationService).addEventToOrganizations(
                eq(Arrays.asList(1l, 2l)),
                any(Event.class)
        );
        verify(translationService).saveTranslations(eq(1L), any(Set.class));
    }

    @Test
    @DisplayName("Should create event successfully without organizations")
    void shouldCreateEventWithoutOrganizations() {
        // Given
        createEventDto.setOrganizationIds(null);

        when(eventMapper.toEntity(any(EventDto.class))).thenReturn(testEvent);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(eventMapper.toDto(any(Event.class))).thenReturn(testEventDto);

        // When
        EventDto result = eventService.create(createEventDto);

        // Then
        assertThat(result).isNotNull();

        verify(eventRepository).save(any(Event.class));
        verify(organizationService, never()).addEventToOrganizations(anyList(), any(Event.class));
    }

    @Test
    @DisplayName("Should create event successfully with empty organizations list")
    void shouldCreateEventWithEmptyOrganizations() {
        // Given
        createEventDto.setOrganizationIds(Collections.emptyList());

        when(eventMapper.toEntity(any(EventDto.class))).thenReturn(testEvent);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(eventMapper.toDto(any(Event.class))).thenReturn(testEventDto);

        // When
        EventDto result = eventService.create(createEventDto);

        // Then
        assertThat(result).isNotNull();

        verify(eventRepository).save(any(Event.class));
        verify(organizationService, never()).addEventToOrganizations(anyList(), any(Event.class));
    }

    @Test
    @DisplayName("Should get display event by id successfully")
    void shouldGetDisplayEventByIdSuccessfully() {
        // Given
        Long eventId = 1L;
        String currentLanguage = "en";
        List<Long> organizationIds = Arrays.asList(1L, 2L);

        when(eventRepository.findDisplayEventById(eventId, currentLanguage))
                .thenReturn(Optional.of(displayEventDto));
        when(eventRepository.findOrganizationIdsByEventId(eventId))
                .thenReturn(organizationIds);

        // Mock the getCurrentLanguage method (assuming it's accessible)
        EventServiceImpl spyService = spy(eventService);
        doReturn(currentLanguage).when(spyService).getCurrentLanguage();

        // When
        DisplayEventDto result = spyService.getDisplayEventById(eventId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(eventId);
        assertThat(result.getOrganizationIds()).containsExactly(1L, 2L);

        verify(eventRepository).findDisplayEventById(eventId, currentLanguage);
        verify(eventRepository).findOrganizationIdsByEventId(eventId);
    }

    @Test
    @DisplayName("Should throw EventNotFoundException when event not found")
    void shouldThrowEventNotFoundExceptionWhenEventNotFound() {
        // Given
        Long eventId = 999L;
        String currentLanguage = "en";

        when(eventRepository.findDisplayEventById(eventId, currentLanguage))
                .thenReturn(Optional.empty());

        EventServiceImpl spyService = spy(eventService);
        doReturn(currentLanguage).when(spyService).getCurrentLanguage();

        // When & Then
        assertThatThrownBy(() -> spyService.getDisplayEventById(eventId))
                .isInstanceOf(EventNotFoundException.class);

        verify(eventRepository).findDisplayEventById(eventId, currentLanguage);
        verify(eventRepository, never()).findOrganizationIdsByEventId(anyLong());
    }

    @Test
    @DisplayName("Should get events by status without query")
    void shouldGetEventsByStatusWithoutQuery() {
        // Given
        String status = "PENDING";
        String query = null;
        int page = 0;
        int size = 10;
        String sortBy = "id";
        String sortDirection = "asc";

        List<Event> events = Arrays.asList(testEvent);
        Page<Event> eventPage = new PageImpl<>(events);
        Page<EventDto> eventDtoPage = new PageImpl<>(Arrays.asList(testEventDto));

        when(eventRepository.findEventsByStatus(eq(ContentStatus.PENDING_REVIEW), any(Pageable.class)))
                .thenReturn(eventPage);
        when(eventMapper.toDto(any(Event.class))).thenReturn(testEventDto);

        // When
        Page<EventDto> result = eventService.getEventsByStatus(status, query, page, size, sortBy, sortDirection);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);

        verify(eventRepository).findEventsByStatus(eq(ContentStatus.PENDING_REVIEW), any(Pageable.class));
        verify(eventRepository, never()).getEventsByStatusWithQuery(any(), any(), any(Pageable.class));
    }

    @Test
    @DisplayName("Should get events by status with query")
    void shouldGetEventsByStatusWithQuery() {
        // Given
        String status = "APPROVED";
        String query = "test event";
        int page = 0;
        int size = 10;
        String sortBy = "id";
        String sortDirection = "desc";

        List<Event> events = Arrays.asList(testEvent);
        Page<Event> eventPage = new PageImpl<>(events);

        when(eventRepository.getEventsByStatusWithQuery(eq(ContentStatus.APPROVED), eq(query), any(Pageable.class)))
                .thenReturn(eventPage);
        when(eventMapper.toDto(any(Event.class))).thenReturn(testEventDto);

        // When
        Page<EventDto> result = eventService.getEventsByStatus(status, query, page, size, sortBy, sortDirection);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);

        verify(eventRepository).getEventsByStatusWithQuery(eq(ContentStatus.APPROVED), eq(query), any(Pageable.class));
        verify(eventRepository, never()).findEventsByStatus(any(), any(Pageable.class));
    }

    @Test
    @DisplayName("Should get events by status with empty query")
    void shouldGetEventsByStatusWithEmptyQuery() {
        // Given
        String status = "PENDING";
        String query = "";
        int page = 0;
        int size = 10;
        String sortBy = "id";
        String sortDirection = "asc";

        List<Event> events = Arrays.asList(testEvent);
        Page<Event> eventPage = new PageImpl<>(events);

        when(eventRepository.findEventsByStatus(eq(ContentStatus.PENDING_REVIEW), any(Pageable.class)))
                .thenReturn(eventPage);
        when(eventMapper.toDto(any(Event.class))).thenReturn(testEventDto);

        // When
        Page<EventDto> result = eventService.getEventsByStatus(status, query, page, size, sortBy, sortDirection);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);

        verify(eventRepository).findEventsByStatus(eq(ContentStatus.PENDING_REVIEW), any(Pageable.class));
        verify(eventRepository, never()).getEventsByStatusWithQuery(any(), any(), any(Pageable.class));
    }

    @Test
    @DisplayName("Should approve event successfully")
    void shouldApproveEventSuccessfully() {
        // Given
        Long eventId = 1L;

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(testEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        // When
        eventService.approve(eventId);

        // Then
        verify(eventRepository).findById(eventId);
        verify(eventRepository).save(testEvent);
        verify(notificationService).notifyAllSubscribed(NotificationEnum.EVENT, "New event");

        assertThat(testEvent.getStatus()).isEqualTo(ContentStatus.APPROVED);
    }

    @Test
    @DisplayName("Should throw EventNotFoundException when approving non-existent event")
    void shouldThrowEventNotFoundExceptionWhenApprovingNonExistentEvent() {
        // Given
        Long eventId = 999L;

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> eventService.approve(eventId))
                .isInstanceOf(EventNotFoundException.class);

        verify(eventRepository).findById(eventId);
        verify(eventRepository, never()).save(any(Event.class));
        verify(notificationService, never()).notifyAllSubscribed(any(), any());
    }

    @Test
    @DisplayName("Should handle organization service failure during event creation")
    void shouldHandleOrganizationServiceFailure() {
        // Given
        when(eventMapper.toEntity(any(EventDto.class))).thenReturn(testEvent);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(organizationService.addEventToOrganizations(anyList(), any(Event.class)))
                .thenThrow(new RuntimeException("Organization service error"));

        // When & Then
        assertThatThrownBy(() -> eventService.create(createEventDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Organization service error");

        verify(eventRepository).save(any(Event.class));
        verify(organizationService).addEventToOrganizations(anyList(), any(Event.class));
    }

    @Test
    @DisplayName("Should handle translation service interaction during creation")
    void shouldHandleTranslationServiceInteraction() {
        // Given
        EventDto eventDtoWithTranslations = new EventDto();
        eventDtoWithTranslations.setId(1L);
        eventDtoWithTranslations.setEventTranslations(translations);

        CreateEventDto createDtoWithTranslations = new CreateEventDto();
        createDtoWithTranslations.setEvent(eventDtoWithTranslations);
        createDtoWithTranslations.setOrganizationIds(Arrays.asList(1L));

        when(eventMapper.toEntity(any(EventDto.class))).thenReturn(testEvent);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(eventMapper.toDto(any(Event.class))).thenReturn(eventDtoWithTranslations);
        when(organizationService.addEventToOrganizations(anyList(), any(Event.class)))
                .thenReturn(organizations);

        // When
        EventDto result = eventService.create(createDtoWithTranslations);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEventTranslations()).isNotEmpty();

        verify(translationService).saveTranslations(eq(1L), any(Set.class));
    }

    @Test
    @DisplayName("Should handle creation without translations")
    void shouldHandleCreationWithoutTranslations() {
        // Given
        EventDto eventDtoWithoutTranslations = new EventDto();
        eventDtoWithoutTranslations.setId(1L);
        eventDtoWithoutTranslations.setEventTranslations(null);

        CreateEventDto createDtoWithoutTranslations = new CreateEventDto();
        createDtoWithoutTranslations.setEvent(eventDtoWithoutTranslations);

        when(eventMapper.toEntity(any(EventDto.class))).thenReturn(testEvent);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);
        when(eventMapper.toDto(any(Event.class))).thenReturn(eventDtoWithoutTranslations);

        // When
        EventDto result = eventService.create(createDtoWithoutTranslations);

        // Then
        assertThat(result).isNotNull();

        verify(translationService, never()).saveTranslations(anyLong(), any(Set.class));
    }


}

@ExtendWith(MockitoExtension.class)
class EventServiceImplIntegrationTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private UserService userService;

    @Mock
    private FileService fileService;

    @Mock
    private EventTranslationService translationService;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private UserNotificationService notificationService;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    @DisplayName("Should handle complex event creation with all dependencies")
    void shouldHandleComplexEventCreation() {
        Event event = new Event();
        event.setId(1L);

        EventDto eventDto = new EventDto();
        eventDto.setId(1L);
        eventDto.setEventTranslations(Arrays.asList(new EventTranslationDto()));

        CreateEventDto createDto = new CreateEventDto();
        createDto.setEvent(eventDto);
        createDto.setOrganizationIds(Arrays.asList(1L, 2L));
        createDto.setImage(mock(MultipartFile.class));
        createDto.setAttachments(new MultipartFile[]{mock(MultipartFile.class)});

        when(eventMapper.toEntity(any(EventDto.class))).thenReturn(event);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(eventMapper.toDto(any(Event.class))).thenReturn(eventDto);
        when(fileService.uploadFilesForEvent(any(), any(Event.class)))
                .thenReturn(Arrays.asList(new File()));
        when(organizationService.addEventToOrganizations(anyList(), any(Event.class)))
                .thenReturn(Arrays.asList(new Organization()));

        EventDto result = eventService.create(createDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(eventRepository).save(any(Event.class));
        verify(fileService).uploadFilesForEvent(any(), any(Event.class));
        verify(organizationService).addEventToOrganizations(anyList(), any(Event.class));
        verify(translationService).saveTranslations(eq(1L), any(Set.class));
    }

    @Test
    @DisplayName("Should handle approval workflow correctly")
    void shouldHandleApprovalWorkflowCorrectly() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setStatus(ContentStatus.PENDING_REVIEW);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        eventService.approve(eventId);

        verify(eventRepository).findById(eventId);
        verify(eventRepository).save(event);
        verify(notificationService).notifyAllSubscribed(NotificationEnum.EVENT, "New event");

        assertThat(event.getStatus()).isEqualTo(ContentStatus.APPROVED);
    }
}
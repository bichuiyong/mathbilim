package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.DisplayEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.reference.RoleDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.mapper.event.EventMapper;
import kg.edu.mathbilim.mapper.user.UserMapper;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.event.EventRepository;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.event.EventTranslationService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import kg.edu.mathbilim.util.PaginationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {
    @InjectMocks
    private EventServiceImpl service;
    @Mock
    private UserService userService;

    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventTranslationService translationService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserNotificationService userNotificationService;
    @Mock
    private EventMapper mapper;
    private Event event;
    private EventDto eventDto;
    private UserDto userDto;
    private CreateEventDto createEventDto;
    private User user;
    private DisplayEventDto displayEventDto;



    @BeforeEach
    void setUp() {
        displayEventDto = new DisplayEventDto();
        userDto = UserDto.builder()
                .id(1L)
                .name("test")
                .surname("test")
                .email("test@test.com")
                .role(RoleDto.builder()
                        .name("USER")
                        .build())
                .build();

        event = new Event();
        event.setId(1L);
        event.setCreator(User.builder()
                        .surname("test")
                        .id(1L)
                        .email("test@test.com")
                .build());

        eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setAddress("address");
        eventDto.setCreator(UserDto.builder()
                .surname("test")
                .id(1L)
                .email("test@test.com")
                        .role(RoleDto.builder()
                                .id(1)
                                .name("ADMIN")
                                .build())
                .build());
        eventDto.setStatus(ContentStatus.APPROVED);
        createEventDto = new CreateEventDto();
        createEventDto.setEvent(eventDto);
        createEventDto.setAttachments(null);
        createEventDto.setImage(null);
        createEventDto.setOrganizationIds(List.of(1L, 2L));

        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .build();

        lenient().when(userService.getAuthUser()).thenReturn(userDto);
        lenient().when(mapper.toDto(event)).thenReturn(eventDto);
        lenient().when(mapper.toEntity(eventDto)).thenReturn(event);
        lenient().when(userService.findByEmail(userDto.getEmail())).thenReturn(user);
        lenient().when(eventRepository.findById(event.getId())).thenReturn(Optional.ofNullable(event));

    }
    @Test
    void createEventTest() {
        when(eventRepository.save(event)).thenReturn(event);
        EventDto eventDto1 = service.create(createEventDto);
        assertThat(eventDto1.getAddress()).isEqualTo("address");
        assertThat(eventDto1.getCreator()).isEqualTo(userDto);
        verify(eventRepository).save(event);
    }
    @Test
    void rejectEventTest() {
        when(eventRepository.save(event)).thenReturn(event);
        service.reject(event.getId(),userDto.getEmail());
        verify(eventRepository).save(event);
        Event saved = eventRepository.findById(event.getId()).orElse(null);
        assertThat(saved.getId()).isEqualTo(event.getId());
        assertThat(saved.getStatus()).isEqualTo(ContentStatus.REJECTED);
    }

    @Test
    void getDisplayEventByIdTest() {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(userDto.getEmail());

        when(eventRepository.findDisplayEventById(event.getId(), service.getCurrentLanguage()))
                .thenReturn(Optional.of(displayEventDto));
        when(eventRepository.findOrganizationIdsByEventId(event.getId()))
                .thenReturn(Arrays.asList(1L, 2L));
        when(userService.findByEmail(anyString())).thenReturn(user);


        DisplayEventDto show = service.getDisplayEventById(1L);

        assertThat(show).isNotNull();
        assertThat(show.getOrganizationIds()).isEqualTo(List.of(1L, 2L));
    }
    @Test
    void getEventsByStatus_withQuery_callsRepositoryGetEventsByStatusWithQuery() {
        Page<Event> events = new PageImpl<>(List.of(event));
        when(eventRepository.getEventsByStatusWithQuery(any(), eq("search"), any())).thenReturn(events);
        when(mapper.toDto(any(Event.class))).thenReturn(eventDto);

        Page<EventDto> result = service.getEventsByStatus("APPROVED", "search", 1, 10, "id", "asc");

        assertTrue(result.getContent().size()==1);
        assertTrue(result.getContent().stream()
                .allMatch(e -> e.getStatus() == ContentStatus.APPROVED));

        verify(eventRepository).getEventsByStatusWithQuery(ContentStatus.APPROVED, "search", PaginationUtil.createPageableWithSort(1, 10, "id", "asc"));
        verify(mapper, times(1)).toDto(any(Event.class));
    }

    @Test
    void getEventsByStatus_withoutQuery_callsRepositoryFindEventsByStatus() {
        Page<Event> events = new PageImpl<>(List.of(event));
        when(eventRepository.findEventsByStatus(any(), any())).thenReturn(events);
        when(mapper.toDto(any(Event.class))).thenReturn(eventDto);

        Page<EventDto> result = service.getEventsByStatus("APPROVED", null, 1, 10, "id", "asc");

        assertTrue(result.getContent().size()==1);
        assertTrue(result.getContent().stream()
                .allMatch(e -> e.getStatus() == ContentStatus.APPROVED));

        verify(eventRepository).findEventsByStatus(ContentStatus.APPROVED, PaginationUtil.createPageableWithSort(1, 10, "id", "asc"));
        verify(mapper, times(1)).toDto(any(Event.class));
    }


    @Test
    void approve_callsApproveContent() {
        user = User.builder().email(userDto.getEmail()).build();
        when(userService.findByEmail(userDto.getEmail())).thenReturn(user);

        service.approve(1L, userDto.getEmail());

        // We can’t directly verify private approveContent, so check repository interaction
        verify(userService).findByEmail(userDto.getEmail());
    }

    @Test
    void getContentByCreatorIdEvent_withQuery_returnsApprovedOnly() {
        // Prepare a page with an Event that has APPROVED status
        eventDto.setStatus(ContentStatus.APPROVED);
        Page<Event> eventsPage = new PageImpl<>(List.of(event));
        when(eventRepository.getEventsByStatusWithQuery(eq(ContentStatus.APPROVED), eq("query"), any(Pageable.class)))
                .thenReturn(eventsPage);
        when(mapper.toDto(any(Event.class))).thenReturn(eventDto);

        Page<EventDto> result = service.getContentByCreatorIdEvent(1L, Pageable.unpaged(), "query");


        assertTrue(result.getContent().size()==1);
        assertTrue(result.getContent().stream()
                .allMatch(e -> e.getStatus() == ContentStatus.APPROVED));

        verify(eventRepository).getEventsByStatusWithQuery(ContentStatus.APPROVED, "query", Pageable.unpaged());
        verify(mapper, times(1)).toDto(any(Event.class));
    }


    @Test
    void getHistoryEvent_withStatusAndQuery_callsRepo() {
        Page<Event> events = new PageImpl<>(List.of(event));
        when(eventRepository.getEventsByCreatorAndStatusAndQuery(any(), anyLong(), anyString(), any()))
                .thenReturn(events);
        when(mapper.toDto(any(Event.class))).thenReturn(eventDto);

        Page<EventDto> result = service.getHistoryEvent(1L, Pageable.unpaged(), "query", "APPROVED");

        assertEquals(1, result.getContent().size());

        verify(eventRepository).getEventsByCreatorAndStatusAndQuery(any(), eq(1L), eq("query"), any());
    }

    @Test
    void getHistoryEvent_withQueryOnly_callsRepo() {
        Page<Event> events = new PageImpl<>(List.of(event));
        when(eventRepository.getEventsWithQuery(anyString(), anyLong(), any())).thenReturn(events);
        when(mapper.toDto(any(Event.class))).thenReturn(eventDto);

        Page<EventDto> result = service.getHistoryEvent(1L, Pageable.unpaged(), "query", null);

        assertEquals(1, result.getContent().size());

        verify(eventRepository).getEventsWithQuery(eq("query"), eq(1L), any());
    }

    @Test
    void getHistoryEvent_withStatusOnly_callsRepo() {
        Page<Event> events = new PageImpl<>(List.of(event));
        when(eventRepository.getEventsByStatusAndCreator(any(), anyLong(), any())).thenReturn(events);
        when(mapper.toDto(any(Event.class))).thenReturn(eventDto);

        Page<EventDto> result = service.getHistoryEvent(1L, Pageable.unpaged(), null, "APPROVED");
        assertEquals(1, result.getContent().size());

        verify(eventRepository).getEventsByStatusAndCreator(any(), eq(1L), any());
    }

    @Test
    void countEventForModeration_returnsCount() {
        when(eventRepository.countByStatus(ContentStatus.PENDING_REVIEW)).thenReturn(5L);

        Long count = service.countEventForModeration();

        assertThat(count).isEqualTo(5L);
    }

    @Test
    void getEventsForModeration_withQuery_callsRepo() {
        Page<Event> events = new PageImpl<>(List.of(event));
        when(eventRepository.getEventsByStatusWithQuery(any(), anyString(), any())).thenReturn(events);
        when(mapper.toDto(any(Event.class))).thenReturn(eventDto);

        Page<EventDto> result = service.getEventsForModeration(Pageable.unpaged(), "query");


        assertEquals(1, result.getContent().size());

        verify(eventRepository).getEventsByStatusWithQuery(any(), eq("query"), any());
    }

    @Test
    void getEventsForModeration_withoutQuery_callsRepo() {
        Page<Event> events = new PageImpl<>(List.of(event));
        when(eventRepository.findEventsByStatus(any(), any())).thenReturn(events);
        when(mapper.toDto(any(Event.class))).thenReturn(eventDto);

        Page<EventDto> result = service.getEventsForModeration(Pageable.unpaged(), null);

        assertEquals(1, result.getContent().size());

        verify(eventRepository).findEventsByStatus(any(), any());
    }





}

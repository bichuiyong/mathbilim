package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.reference.RoleDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.mapper.event.EventMapper;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.event.EventRepository;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.event.EventTranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    private EventMapper mapper;
    private Event event;
    private EventDto eventDto;
    private UserDto userDto;
    private CreateEventDto createEventDto;



    @BeforeEach
    void setUp() {
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
        createEventDto = new CreateEventDto();
        createEventDto.setEvent(eventDto);
        createEventDto.setAttachments(null);
        createEventDto.setImage(null);
        createEventDto.setOrganizationIds(List.of(1L, 2L));

        lenient().when(userService.getAuthUser()).thenReturn(userDto);
        lenient().when(mapper.toDto(event)).thenReturn(eventDto);
        lenient().when(mapper.toEntity(eventDto)).thenReturn(event);

    }
    @Test
    void createEventTest() {
        when(eventRepository.save(event)).thenReturn(event);
        EventDto eventDto1 = service.create(createEventDto);
        assertThat(eventDto1.getAddress()).isEqualTo("address");
        assertThat(eventDto1.getCreator()).isEqualTo(userDto);
        verify(eventRepository).save(event);

    }



}

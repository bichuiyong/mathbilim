package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.EventTypeDto;
import kg.edu.mathbilim.dto.event.EventTypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TypeNotFoundException;
import kg.edu.mathbilim.mapper.event.EventTypeMapper;
import kg.edu.mathbilim.model.event.EventType;
import kg.edu.mathbilim.model.event.EventTypeTranslation;
import kg.edu.mathbilim.repository.event.EventTypeRepository;
import kg.edu.mathbilim.repository.event.EventTypeTranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventTypeServiceImplTest {
    @Mock
    private EventTypeRepository eventTypeRepository;
    @InjectMocks
    private EventTypeServiceImpl service;
    @Mock
    private EventTypeMapper mapper;
    @Mock
    private EventTypeTranslationRepository translationRepository;

    private EventType eventType;
    private EventTypeDto eventTypeDto;
    private EventTypeTranslation eventTypeTranslation;


    @BeforeEach
    void setUp() {
        eventType = new EventType();
        eventType.setId(1);
        eventType.setTranslations(new ArrayList<>());


        eventTypeDto = new EventTypeDto();
        eventTypeDto.setId(1);
        eventTypeDto.setTranslations(List.of(
                EventTypeTranslationDto.builder()
                        .typeId(1)
                        .languageCode("ru")
                        .translation("translation1")
                        .build()));

        eventTypeTranslation = new EventTypeTranslation();
        eventTypeTranslation.setTranslation("translation2");
    }

    @Test
    void createEventTypeTest() {
        EventType newEventType = new EventType();
        newEventType.setId(1);

        when(eventTypeRepository.save(any(EventType.class))).thenReturn(newEventType);
        when(mapper.toDto(any(EventType.class))).thenReturn(eventTypeDto);
        when(mapper.toTranslationEntity(any(EventTypeTranslationDto.class), any(EventType.class)))
                .thenReturn(eventTypeTranslation);

        EventTypeDto p = service.createEventType(eventTypeDto);

        assertThat(p.getId()).isEqualTo(1);
        assertThat(p.getTranslations().size()).isEqualTo(1);
    }

    @Test
    void updateEventTypeTest() {
        EventType newEventType = new EventType();
        newEventType.setId(2);
        Integer id = 1;
        newEventType.setTranslations(new ArrayList<>());

        EventTypeDto newEventTypeDto = new EventTypeDto();
        newEventTypeDto.setId(newEventType.getId());
        newEventTypeDto.setTranslations(List.of());
        when(eventTypeRepository.findById(id)).thenReturn(Optional.ofNullable(eventType));
        when(mapper.toDto(any(EventType.class))).thenReturn(newEventTypeDto);

        EventTypeDto saved = service.updateEventType(id, eventTypeDto);

        assertThat(saved.getId()).isEqualTo(2);
        assertThat(saved.getTranslations().size()).isEqualTo(0);
    }

    @Test
    void correctDeletingEventTypeTest() {
        service.deleteEventType(eventType.getId());
        verify(eventTypeRepository).deleteById(eventType.getId());
    }

    @Test
    void getPostTypesByLanguageTest() {
        String language = "en";
        List<EventTypeDto> f = service.getEventTypesByLanguage(language);
        assertThat(f.size()).isEqualTo(0);
        verify(eventTypeRepository, times(1)).findAll();
    }

    @Test
    void getAllPostTypesWithQueryTest() {
        String name = "dd";
        String language = "en";

        when(eventTypeRepository.findAllByQuery(name, language)).thenReturn(new ArrayList<>());

        List<EventTypeDto> result = service.getAllEventTypesByQuery(name, language);

        assertThat(result.size()).isEqualTo(0);
        verify(eventTypeRepository, times(1)).findAllByQuery(name, language);
    }

    @Test
    void getAllPostTypesTest() {
        when(eventTypeRepository.findAll()).thenReturn(new ArrayList<>());

        List<EventTypeDto> all = service.getAllEventTypes();

        assertThat(all.size()).isEqualTo(0);
        verify(eventTypeRepository, times(1)).findAll();
    }

    @Test
    void addEventTypeTranslationTest() {
        String language = "en";
        String translation = "translation1";
        Integer eventTypeId = eventType.getId();

        when(eventTypeRepository.findById(eventTypeId)).thenReturn(Optional.of(eventType));
        when(mapper.toDto(eventType)).thenReturn(eventTypeDto);
        when(mapper.toTranslationEntity(any(EventTypeTranslationDto.class), eq(eventType)))
                .thenReturn(eventTypeTranslation);

        EventTypeDto eventTypeDto1 = service.addTranslation(eventType.getId(), language, translation);

        verify(eventTypeRepository, times(2)).findById(eventTypeId);
        verify(mapper, times(1)).toTranslationEntity(any(EventTypeTranslationDto.class), eq(eventType));
        verify(mapper, times(1)).toDto(eventType);

        assertThat(eventTypeDto1).isNotNull();
    }

    @Test
    void removeEventTypeTranslationTest() {
        String language = "en";
        assertThatThrownBy(() ->  service.removeTranslation(eventType.getId(), language)).isInstanceOf(TypeNotFoundException.class);

    }




}

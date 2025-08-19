package kg.edu.mathbilim.service.impl.event;

import jakarta.inject.Inject;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.mapper.event.EventTranslationMapper;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.event.EventTranslation;
import kg.edu.mathbilim.model.event.EventTranslationId;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.event.EventTranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventTranslationServiceImplTest {

    @InjectMocks
    private EventTranslationServiceImpl service;
    @Mock
    private EventTranslationMapper mapper;
    @Mock
    private EventTranslationRepository repository;

    private EventTranslation translation;
    private EventTranslationDto translationDto;
    private Event event;

    @BeforeEach
    void setUp() {
        translation = new EventTranslation();
        translationDto = new EventTranslationDto();
        event = new Event();
        event.setId(1L);
        event.setCreator(User.builder()
                .id(1L)
                .email("test@test.com")
                .build());

        translation.setEvent(event);
        translation.setTitle("test title");
        translationDto.setTitle("test title");
        translation.setId(new EventTranslationId());
        translationDto.setEventId(event.getId());
        translationDto.setContent("content");
        translationDto.setLanguageCode("ru");
        translationDto.setTitle("title");
    }

    @Test
    void isCorrectEventTranslationSaving() {
        service.saveTranslations(event.getId(), Set.of(translationDto));


        assertThat(translation).isNotNull();
        assertThat(translation.getId()).isNotNull();

        EventTranslationDto p = service.createTranslation(translationDto);

        assertThat(p).isNotNull();
        assertEquals(p.getEventId(), translationDto.getEventId());
        assertEquals(p.getLanguageCode(), translationDto.getLanguageCode());
        assertEquals(p.getTitle(), translationDto.getTitle());
    }
    @Test
    void isCorrecEventTranlsationstUpdate(){
        service.upsertTranslation(translationDto);
        assertThat(translation).isNotNull();
        assertThat(translation.getId()).isNotNull();

    }


    @Test
    void isCorrectEventTranlsationsDelete(){
        service.deleteAllTranslationsByEntityId(event.getId());
        verify(repository, times(1)).deleteByEventId(event.getId());

    }

    @Test
    void isCorrectPostTranslationName(){
        assertEquals("event", service.getEntityName());
    }

    @Test
    void isCorrectPostTranslationIdFromDto(){
        long id = service.getEntityIdFromDto(translationDto);
        assertEquals(event.getId(), id);
    }


}

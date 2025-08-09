package kg.edu.mathbilim.service.impl.event;

import kg.edu.mathbilim.dto.event.EventTypeTranslationDto;
import kg.edu.mathbilim.dto.post.PostTypeTranslationDto;
import kg.edu.mathbilim.mapper.event.EventTypeTranslationMapper;
import kg.edu.mathbilim.mapper.post.PostTypeTranslationMapper;
import kg.edu.mathbilim.repository.event.EventTypeTranslationRepository;
import kg.edu.mathbilim.repository.post.PostTypeTranslationRepository;
import kg.edu.mathbilim.service.impl.post.PostTypeTranslationServiceImpl;
import kg.edu.mathbilim.service.interfaces.event.EventTypeTranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventTypeTranslationImplTest {
    @InjectMocks
    private EventTypeTranslationServiceImpl service;
    @Mock
    private EventTypeTranslationRepository translationRepository;

    @Mock
    private EventTypeTranslationMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new EventTypeTranslationServiceImpl(translationRepository, mapper);
    }

    @Test
    void getPostTypeTranslationsByTypeId() {
        Integer typeId = 1;
        List<EventTypeTranslationDto> list = service.getTranslationsByEventTypeId(typeId);
        assertThat(list.size()).isEqualTo(0);
        verify(translationRepository, Mockito.times(1)).findByTypeId(typeId);
    }
    @Test
    void deletePostTypeTranslationsByTypeId() {
        Integer typeId = 1;
        service.deleteAllTranslationsByTypeId(typeId);
        verify(translationRepository, Mockito.times(1)).deleteByTypeId(typeId);

    }

}

package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.PostTypeTranslationDto;
import kg.edu.mathbilim.mapper.post.PostTypeTranslationMapper;
import kg.edu.mathbilim.repository.post.PostTypeTranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

class PostTypeTranslationServiceTest {

    private PostTypeTranslationServiceImpl service;

    @Mock
    private PostTypeTranslationRepository translationRepository;

    @Mock
    private PostTypeTranslationMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PostTypeTranslationServiceImpl(translationRepository, mapper);
    }

    @Test
    void getPostTypeTranslationsByTypeId() {
        Integer typeId = 1;
        List<PostTypeTranslationDto> list = service.getTranslationsByPostTypeId(typeId);
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

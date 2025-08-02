package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.mapper.post.PostTranslationMapper;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.model.post.PostTranslation;
import kg.edu.mathbilim.model.post.PostTranslationId;
import kg.edu.mathbilim.repository.post.PostTranslationRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostTranslationServiceTest {
    @InjectMocks
    private PostTranslationServiceImpl service;

    @Mock
    private PostTranslationRepository repository;

    @Mock
    private PostTranslationMapper mapper;




    private PostTranslation postTranslation;
    private PostTranslationDto postTranslationDto;
    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setPostTranslations(new ArrayList<>());


        postTranslation = new PostTranslation();
        postTranslation.setPost(post);
        postTranslation.setId(new PostTranslationId());
        postTranslation.setTitle("title");
        postTranslation.setContent("content");

        postTranslationDto = new PostTranslationDto();
        postTranslationDto.setPostId(post.getId());
        postTranslationDto.setContent(postTranslation.getContent());
        postTranslationDto.setTitle(postTranslation.getTitle());
        postTranslationDto.setLanguageCode("en");




    }
    @Test
    void isCorrectPostTranlsationsSaving(){

        service.saveTranslations(post.getId(), Set.of(postTranslationDto));

        assertThat(postTranslation).isNotNull();
        assertThat(postTranslation.getId()).isNotNull();

        PostTranslationDto p = service.createTranslation(postTranslationDto);

        assertThat(p).isNotNull();
        assertEquals(p.getPostId(),postTranslationDto.getPostId());
        assertEquals(p.getLanguageCode(),postTranslationDto.getLanguageCode());
        assertEquals(p.getTitle(),postTranslationDto.getTitle());


    }

    @Test
    void isCorrecPostTranlsationstUpdate(){
        service.upsertTranslation(postTranslationDto);
        assertThat(postTranslation).isNotNull();
        assertThat(postTranslation.getId()).isNotNull();

    }

    @Test
    void isCorrectPostTranlsationsDelete(){
        service.deleteAllTranslationsByEntityId(post.getId());
        verify(repository, times(1)).deleteByPostId(post.getId());

    }

    @Test
    void isCorrectPostTranslationName(){
        assertEquals("post", service.getEntityName());
    }

    @Test
    void isCorrectPostTranslationIdFromDto(){
        long id = service.getEntityIdFromDto(postTranslationDto);
        assertEquals(post.getId(), id);
    }


    }

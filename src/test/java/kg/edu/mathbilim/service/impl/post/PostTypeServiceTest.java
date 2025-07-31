package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.PostTypeDto;
import kg.edu.mathbilim.dto.post.PostTypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TypeNotFoundException;
import kg.edu.mathbilim.mapper.post.PostTypeMapper;
import kg.edu.mathbilim.model.post.PostType;
import kg.edu.mathbilim.model.post.PostTypeTranslation;
import kg.edu.mathbilim.repository.post.PostTypeRepository;
import kg.edu.mathbilim.repository.post.PostTypeTranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class PostTypeServiceTest {

    @Mock
    private PostTypeRepository postTypeRepository;

    @Mock
    private PostTypeTranslationRepository postTranslationRepository;

    @Mock
    private PostTypeMapper postTypeMapper;

    @Mock
    private PostTypeTranslationRepository postTypeTranslationRepository;

    private PostTypeServiceImpl postTypeService;
    private PostType postType;
    private PostTypeDto postTypeDto;
    private PostTypeTranslation postTypeTranslation;

    @BeforeEach
    void setUp() {
        postTypeService = new PostTypeServiceImpl(
                postTypeRepository,
                postTranslationRepository,
                postTypeMapper
        );

        postType = new PostType();
        postType.setId(1);
        postType.setTranslations(new ArrayList<>());

        postTypeDto = new PostTypeDto();
        postTypeDto.setTranslations(List.of(
                PostTypeTranslationDto.builder()
                        .translation("translation")
                        .typeId(1)
                        .languageCode("en")
                        .build()
        ));

        postTypeTranslation = new PostTypeTranslation();
        postTypeTranslation.setTranslation("translation");
    }

    @Test
    void getAllPostTypesWithQueryTest() {
        String name = "dd";
        String language = "en";

        when(postTypeRepository.findAllByQuery(name, language)).thenReturn(new ArrayList<>());

        List<PostTypeDto> result = postTypeService.getAllPostTypesWithQuery(name, language);

        assertThat(result.size()).isEqualTo(0);
        verify(postTypeRepository, times(1)).findAllByQuery(name, language);
    }

    @Test
    void getAllPostTypesTest() {
        when(postTypeRepository.findAll()).thenReturn(new ArrayList<>());

        List<PostTypeDto> all = postTypeService.getAllPostTypes();

        assertThat(all.size()).isEqualTo(0);
        verify(postTypeRepository, times(1)).findAll();
    }

    @Test
    void getPostTypeByIdTest() {
        Integer id = 1;
        when(postTypeRepository.findById(id)).thenReturn(Optional.of(postType));
        when(postTypeMapper.toDto(postType)).thenReturn(postTypeDto);

        PostTypeDto result = postTypeService.getPostTypeById(id);

        assertThat(result).isNotNull();
        verify(postTypeRepository, times(1)).findById(id);
        verify(postTypeMapper, times(1)).toDto(postType);
    }

    @Test
    void savePostTypeTest() {
        PostType newPostType = new PostType();
        newPostType.setId(1);


        when(postTypeRepository.save(any(PostType.class))).thenReturn(newPostType);
        when(postTypeMapper.toTranslationEntity(any(PostTypeTranslationDto.class), any(PostType.class)))
                .thenReturn(postTypeTranslation);
        when(postTypeMapper.toDto(any())).thenReturn(postTypeDto);


        PostTypeDto result = postTypeService.createPostType(postTypeDto);


        verify(postTypeRepository, times(1)).save(any(PostType.class));
        verify(postTranslationRepository, times(1)).save(any(PostTypeTranslation.class));
        verify(postTypeMapper, times(1)).toTranslationEntity(any(PostTypeTranslationDto.class), any(PostType.class));
        verify(postTypeMapper).toDto(any(PostType.class));

        assertThat(result).isNotNull();
    }

    @Test
    void getPostTypesByLanguageTest() {
        String language = "en";
        List<PostTypeDto> f = postTypeService.getPostTypesByLanguage(language);
        assertThat(f.size()).isEqualTo(0);
        verify(postTypeRepository, times(1)).findAll();
    }

    @Test
    void deletePostTypeByIdTest() {
        Integer id = 1;

        postTypeService.deletePostType(id);
        verify(postTypeRepository, times(1)).deleteById(id);

    }

    @Test
    void updatePostTypeTest() {
        Integer id = 1;

        PostType postType1 = new PostType();
        postType1.setId(id);

        PostTypeDto postTypeDto1 = new PostTypeDto();
        postTypeDto1.setTranslations(List.of(PostTypeTranslationDto.builder()
                .languageCode("en")
                .typeId(id)
                .translation("translation")
                .build()));

        when(postTypeRepository.findById(eq(id))).thenReturn(Optional.of(postType1));
        when(postTypeMapper.toDto(any())).thenReturn(postTypeDto1);
        PostTypeDto result = postTypeService.updatePostType(id, postTypeDto1);

        assertThat(result.getTranslations().get(0).getTranslation()).isEqualTo("translation");
    }


    @Test
    void removePostTypeByPostTypeIdAndLanguageCodeTest() {

        String languageCode = "en";
        Integer id = 1;
        assertThatThrownBy(() -> postTypeService.removeTranslation(id, languageCode)).isInstanceOf(TypeNotFoundException.class);
    }


    @Test
    void addTranslationTest() {
        Integer postTypeId = 1;
        String languageCode = "en";
        String translation = "Sample Translation";

        postType.setId(postTypeId);

        when(postTypeRepository.findById(postTypeId)).thenReturn(Optional.of(postType));
        when(postTypeMapper.toDto(postType)).thenReturn(postTypeDto);
        when(postTypeMapper.toTranslationEntity(any(PostTypeTranslationDto.class), eq(postType)))
                .thenReturn(postTypeTranslation);

        PostTypeDto result = postTypeService.addTranslation(postTypeId, languageCode, translation);

        verify(postTypeRepository, times(2)).findById(postTypeId);
        verify(postTypeMapper, times(1)).toTranslationEntity(any(PostTypeTranslationDto.class), eq(postType));
        verify(postTypeMapper, times(1)).toDto(postType);

        assertThat(result).isNotNull();
    }


}
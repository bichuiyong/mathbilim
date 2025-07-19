package kg.edu.mathbilim.service.impl.post;

import kg.edu.mathbilim.dto.post.PostTypeDto;
import kg.edu.mathbilim.dto.post.PostTypeTranslationDto;
import kg.edu.mathbilim.mapper.post.PostTypeMapper;
import kg.edu.mathbilim.model.post.PostType;
import kg.edu.mathbilim.model.post.PostTypeTranslation;
import kg.edu.mathbilim.repository.post.PostTypeRepository;
import kg.edu.mathbilim.repository.post.PostTypeTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTypeContentService;
import kg.edu.mathbilim.service.interfaces.post.PostTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostTypeServiceImpl
        extends AbstractTypeContentService<
        PostType,
        PostTypeDto,
        PostTypeTranslation,
        PostTypeTranslationDto,
        PostTypeRepository,
        PostTypeTranslationRepository,
        PostTypeMapper>
        implements PostTypeService {
    private final PostTypeRepository postTypeRepository;
    private final PostTypeMapper postTypeMapper;

    public PostTypeServiceImpl(PostTypeRepository repository,
                               PostTypeTranslationRepository translationRepository,
                               PostTypeMapper mapper) {
        super(repository, translationRepository, mapper);
        this.postTypeRepository = repository;
        this.postTypeMapper = mapper;
    }

    @Override
    public List<PostTypeDto> getAllPostTypes() {
        return getAll();
    }

    @Override
    public PostTypeDto getPostTypeById(Integer id) {
        return getByIdOrThrow(id);
    }

    @Override
    public List<PostTypeDto> getPostTypesByLanguage(String languageCode) {
        return getByLanguage(languageCode);
    }

    @Transactional
    @Override
    public PostTypeDto createPostType(PostTypeDto postTypeDto) {
        return create(postTypeDto);
    }

    @Transactional
    @Override
    public PostTypeDto updatePostType(Integer id, PostTypeDto postTypeDto) {
        return update(id, postTypeDto);
    }

    @Transactional
    @Override
    public void deletePostType(Integer id) {
        delete(id);
    }

    @Transactional
    @Override
    public PostTypeDto addTranslation(Integer postTypeId, String languageCode, String translation) {
        return addTranslation(postTypeId, languageCode, translation);
    }

    @Transactional
    @Override
    public PostTypeDto removeTranslation(Integer postTypeId, String languageCode) {
        return removeTranslation(postTypeId, languageCode);
    }

    @Override
    public List<PostTypeDto> getAllPostTypesWithQuery(String name, String lang) {
        return getAllByQuery(name, lang);
//        return postTypeRepository.findAllByQuery(name, lang).stream()
//                .map(postTypeMapper::toDto)
//                .toList();
    }

    @Override
    protected PostType createNewEntity() {
        return new PostType();
    }

    @Override
    protected PostTypeTranslationDto createTranslationDto(Integer typeId, String languageCode, String translation) {
        return PostTypeTranslationDto.builder()
                .typeId(typeId)
                .languageCode(languageCode)
                .translation(translation)
                .build();
    }

    @Override
    protected void setTypeIdInTranslation(PostTypeTranslationDto translationDto, Integer typeId) {
        translationDto.setTypeId(typeId);
    }
}
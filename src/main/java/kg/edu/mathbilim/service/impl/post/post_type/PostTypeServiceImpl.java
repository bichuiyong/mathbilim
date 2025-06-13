package kg.edu.mathbilim.service.impl.post.post_type;

import kg.edu.mathbilim.dto.post.post_type.PostTypeDto;
import kg.edu.mathbilim.dto.post.post_type.PostTypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TypeNotFoundException;
import kg.edu.mathbilim.mapper.post.post_type.PostTypeMapper;
import kg.edu.mathbilim.model.post.post_type.PostType;
import kg.edu.mathbilim.repository.post.post_type.PostTypeRepository;
import kg.edu.mathbilim.service.interfaces.post.post_type.PostTypeService;
import kg.edu.mathbilim.service.interfaces.post.post_type.PostTypeTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostTypeServiceImpl implements PostTypeService {
    private final PostTypeRepository postTypeRepository;
    private final PostTypeMapper postTypeMapper;
    private final PostTypeTranslationService postTypeTranslationService;

    @Override
    public List<PostTypeDto> getAllPostTypes() {
        return postTypeRepository.findAll()
                .stream()
                .map(postTypeMapper::toDto)
                .toList();
    }

    private PostType getPostTypeEntity(Integer id) {
        return postTypeRepository.findById(id)
                .orElseThrow(TypeNotFoundException::new);
    }

    @Override
    public PostTypeDto getPostTypeById(Integer id) {
        return postTypeMapper.toDto(getPostTypeEntity(id));
    }

    @Override
    public List<PostTypeDto> getPostTypesByLanguage(String languageCode) {
        return postTypeRepository.findAll().stream()
                .map(postType -> {
                    PostTypeDto dto = postTypeMapper.toDto(postType);
                    dto.setPostTypeTranslations(List.of(postTypeTranslationService.getTranslation(postType.getId(), languageCode)));
                    return dto;
                })
                .toList();
    }

    @Transactional
    @Override
    public PostTypeDto createPostType(PostTypeDto postTypeDto) {
        PostType postType = new PostType();
        PostType savedPostType = postTypeRepository.save(postType);

        PostTypeDto savedDto = postTypeMapper.toDto(savedPostType);

        if (postTypeDto.getPostTypeTranslations() != null && !postTypeDto.getPostTypeTranslations().isEmpty()) {
            List<PostTypeTranslationDto> savedTranslations = postTypeDto
                    .getPostTypeTranslations()
                    .stream()
                    .map(translation -> {
                        translation.setPostTypeId(savedPostType.getId());
                        return postTypeTranslationService.createTranslation(translation);
                    })
                    .toList();
            savedDto.setPostTypeTranslations(savedTranslations);
        }

        return savedDto;
    }

    @Transactional
    @Override
    public PostTypeDto updatePostType(Integer id, PostTypeDto postTypeDto) {
        PostTypeDto dto = getPostTypeById(id);

        if (postTypeDto.getPostTypeTranslations() != null) {
            postTypeTranslationService.deleteAllTranslationsByPostTypeId(id);

            List<PostTypeTranslationDto> savedTranslations =
                    postTypeDto.getPostTypeTranslations()
                            .stream()
                            .map(translation -> {
                                translation.setPostTypeId(id);
                                return postTypeTranslationService.createTranslation(translation);
                            })
                            .toList();

            dto.setPostTypeTranslations(savedTranslations);
            return dto;
        }

        return dto;
    }

    @Transactional
    @Override
    public void deletePostType(Integer id) {
        postTypeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public PostTypeDto addTranslation(Integer postTypeId, String languageCode, String translation) {
        PostTypeDto postType = getPostTypeById(postTypeId);

        PostTypeTranslationDto translationDto = PostTypeTranslationDto.builder()
                .postTypeId(postTypeId)
                .languageCode(languageCode)
                .translation(translation)
                .build();

        postTypeTranslationService.upsertTranslation(translationDto);
        postType.getPostTypeTranslations().add(translationDto);
        return postType;
    }

    @Transactional
    @Override
    public PostTypeDto removeTranslation(Integer postTypeId, String languageCode) {
        getPostTypeById(postTypeId);
        postTypeTranslationService.deleteTranslation(postTypeId, languageCode);
        return getPostTypeById(postTypeId);
    }
}
package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.post.PostTranslation;
import kg.edu.mathbilim.model.post.PostTranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostTranslationMapper extends BaseMapper<PostTranslation, PostTranslationDto> {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.postId", target = "postId")
    PostTranslationDto toDto(PostTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.postId", source = "postId")
    @Mapping(target = "post.id", source = "postId")
    @Mapping(target = "post.postTranslations", ignore = true)
    PostTranslation toEntity(PostTranslationDto dto);

    @AfterMapping
    default void ensureCompositeKey(@MappingTarget PostTranslation entity, PostTranslationDto dto) {
        if (entity.getId() == null) {
            entity.setId(new PostTranslationId());
            entity.getId().setPostId(dto.getPostId());
            entity.getId().setLanguageCode(dto.getLanguageCode());
        }
    }
}

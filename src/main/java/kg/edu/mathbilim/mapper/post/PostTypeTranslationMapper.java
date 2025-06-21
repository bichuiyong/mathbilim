package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.PostTypeTranslationDto;
import kg.edu.mathbilim.model.post.PostType;
import kg.edu.mathbilim.model.post.PostTypeTranslation;
import kg.edu.mathbilim.model.abstracts.TypeTranslation.TranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostTypeTranslationMapper {

    @Mapping(source = "translationId.languageCode", target = "languageCode")
    @Mapping(source = "translationId.typeId", target = "postTypeId")
    @Mapping(source = "translation", target = "translation")
    PostTypeTranslationDto toDto(PostTypeTranslation entity);

    @Mapping(target = "translationId.languageCode", source = "languageCode")
    @Mapping(target = "translationId.typeId", source = "postTypeId")
    @Mapping(target = "translation", source = "translation")
    @Mapping(target = "postType.id", source = "postTypeId")
    @Mapping(target = "postType.postTypeTranslations", ignore = true)
    PostTypeTranslation toEntity(PostTypeTranslationDto dto);

    @AfterMapping
    default void ensureCompositeKey(@MappingTarget PostTypeTranslation entity, PostTypeTranslationDto dto) {
        if (entity.getTranslationId() == null) {
            entity.setTranslationId(new TranslationId());
        }
        entity.getTranslationId().setTypeId(dto.getPostTypeId());
        entity.getTranslationId().setLanguageCode(dto.getLanguageCode());

        if (entity.getPostType() == null) {
            entity.setPostType(new PostType());
        }
        entity.getPostType().setId(dto.getPostTypeId());
    }
}
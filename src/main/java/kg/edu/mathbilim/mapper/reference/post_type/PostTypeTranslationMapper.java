package kg.edu.mathbilim.mapper.reference.post_type;

import kg.edu.mathbilim.dto.reference.post_type.PostTypeTranslationDto;
import kg.edu.mathbilim.model.reference.post_type.PostTypeTranslation;
import kg.edu.mathbilim.model.reference.post_type.PostTypeTranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostTypeTranslationMapper {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "translation", target = "translation")
    PostTypeTranslationDto toDto(PostTypeTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.postTypeId", source = "postTypeId")
    @Mapping(target = "translation", source = "translation")
    @Mapping(target = "postType.id", source = "postTypeId")
    @Mapping(target = "postType.postTypeTranslations", ignore = true)
    PostTypeTranslation toEntity(PostTypeTranslationDto dto);


    @AfterMapping
    default void ensureCompositeKey(@MappingTarget PostTypeTranslation entity, PostTypeTranslationDto dto) {
        if (entity.getId() == null) {
            entity.setId(new PostTypeTranslationId());
            entity.getId().setPostTypeId(dto.getPostTypeId());
            entity.getId().setLanguageCode(dto.getLanguageCode());
        }
    }
}

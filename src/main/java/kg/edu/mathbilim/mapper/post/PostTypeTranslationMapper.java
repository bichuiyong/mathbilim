package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.PostTypeTranslationDto;
import kg.edu.mathbilim.mapper.BaseTranslationMapper;
import kg.edu.mathbilim.model.post.PostType;
import kg.edu.mathbilim.model.post.PostTypeTranslation;
import kg.edu.mathbilim.model.abstracts.TypeTranslation.TranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostTypeTranslationMapper extends BaseTranslationMapper<PostTypeTranslation, PostTypeTranslationDto> {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "postTypeId")
    PostTypeTranslationDto toDto(PostTypeTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "postTypeId")
    @Mapping(target = "parent.id", source = "postTypeId")
    PostTypeTranslation toEntity(PostTypeTranslationDto dto);
}
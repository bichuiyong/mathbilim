package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.PostTypeTranslationDto;
import kg.edu.mathbilim.mapper.BaseTranslationMapper;
import kg.edu.mathbilim.model.post.PostTypeTranslation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostTypeTranslationMapper extends BaseTranslationMapper<PostTypeTranslation, PostTypeTranslationDto> {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "typeId")
    PostTypeTranslationDto toDto(PostTypeTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "typeId")
    @Mapping(target = "parent.id", source = "typeId")
    PostTypeTranslation toEntity(PostTypeTranslationDto dto);


}
package kg.edu.mathbilim.mapper.user;

import kg.edu.mathbilim.dto.user.UserTypeTranslationDto;
import kg.edu.mathbilim.mapper.BaseTranslationMapper;
import kg.edu.mathbilim.model.user.UserTypeTranslation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserTypeTranslationMapper extends BaseTranslationMapper<UserTypeTranslation, UserTypeTranslationDto> {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "typeId")
    UserTypeTranslationDto toDto(UserTypeTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "typeId")
    @Mapping(target = "parent.id", source = "typeId")
    UserTypeTranslation toEntity(UserTypeTranslationDto dto);

}
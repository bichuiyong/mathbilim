package kg.edu.mathbilim.mapper.user;

import kg.edu.mathbilim.dto.user.UserTypeTranslationDto;
import kg.edu.mathbilim.mapper.BaseTranslationMapper;
import kg.edu.mathbilim.model.user.UserType;
import kg.edu.mathbilim.model.user.UserTypeTranslation;
import kg.edu.mathbilim.model.abstracts.TypeTranslation.TranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserTypeTranslationMapper extends BaseTranslationMapper<UserTypeTranslation, UserTypeTranslationDto> {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "userTypeId")
    UserTypeTranslationDto toDto(UserTypeTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "userTypeId")
    @Mapping(target = "parent.id", source = "userTypeId")
    UserTypeTranslation toEntity(UserTypeTranslationDto dto);
}
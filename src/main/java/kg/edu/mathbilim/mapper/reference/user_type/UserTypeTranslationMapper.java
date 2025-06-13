package kg.edu.mathbilim.mapper.reference.user_type;

import kg.edu.mathbilim.dto.user.user_type.UserTypeTranslationDto;
import kg.edu.mathbilim.model.reference.user_type.UserTypeTranslation;
import kg.edu.mathbilim.model.reference.user_type.UserTypeTranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserTypeTranslationMapper {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "translation", target = "translation")
    UserTypeTranslationDto toDto(UserTypeTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.userTypeId", source = "userTypeId")
    @Mapping(target = "translation", source = "translation")
    @Mapping(target = "userType.id", source = "userTypeId")
    @Mapping(target = "userType.userTypeTranslations", ignore = true)
    UserTypeTranslation toEntity(UserTypeTranslationDto dto);


    @AfterMapping
    default void ensureCompositeKey(@MappingTarget UserTypeTranslation entity, UserTypeTranslationDto dto) {
        if (entity.getId() == null) {
            entity.setId(new UserTypeTranslationId());
            entity.getId().setUserTypeId(dto.getUserTypeId());
            entity.getId().setLanguageCode(dto.getLanguageCode());
        }
    }
}

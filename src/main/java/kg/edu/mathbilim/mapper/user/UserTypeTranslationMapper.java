package kg.edu.mathbilim.mapper.user;

import kg.edu.mathbilim.dto.user.UserTypeTranslationDto;
import kg.edu.mathbilim.model.user.UserType;
import kg.edu.mathbilim.model.user.UserTypeTranslation;
import kg.edu.mathbilim.model.abstracts.TypeTranslation.TranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserTypeTranslationMapper {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "userTypeId")
    @Mapping(source = "translation", target = "translation")
    UserTypeTranslationDto toDto(UserTypeTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "userTypeId")
    @Mapping(target = "translation", source = "translation")
    @Mapping(target = "userType.id", source = "userTypeId")
    @Mapping(target = "userType.userTypeTranslations", ignore = true)
    UserTypeTranslation toEntity(UserTypeTranslationDto dto);

    @AfterMapping
    default void ensureCompositeKey(@MappingTarget UserTypeTranslation entity, UserTypeTranslationDto dto) {
        if (entity.getId() == null) {
            entity.setId(new TranslationId());
        }
        entity.getId().setTypeId(dto.getUserTypeId());
        entity.getId().setLanguageCode(dto.getLanguageCode());

        if (entity.getUserType() == null) {
            entity.setUserType(new UserType());
        }
        entity.getUserType().setId(dto.getUserTypeId());
    }
}
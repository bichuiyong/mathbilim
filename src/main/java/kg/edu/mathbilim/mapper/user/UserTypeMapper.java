package kg.edu.mathbilim.mapper.user;

import kg.edu.mathbilim.dto.user.UserTypeDto;
import kg.edu.mathbilim.dto.user.UserTypeTranslationDto;
import kg.edu.mathbilim.mapper.TypeBaseMapper;
import kg.edu.mathbilim.model.user.UserType;
import kg.edu.mathbilim.model.user.UserTypeTranslation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeMapper extends TypeBaseMapper<
        UserType,
        UserTypeDto,
        UserTypeTranslation,
        UserTypeTranslationDto,
        UserTypeTranslationMapper> {

    @Override
    UserTypeDto toDto(UserType entity);

    @Override
    UserType toEntity(UserTypeDto dto);

    @Override
    default UserTypeTranslationMapper getTranslationMapper() {
        return new UserTypeTranslationMapperImpl();
    }
}

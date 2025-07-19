package kg.edu.mathbilim.mapper.user;

import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.dto.user.UserEditDto;
import kg.edu.mathbilim.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);

    UserDto toDto(User user);

    @Mapping(target = "typeId", source = "type.id")
    UserEditDto toEditDto(User user);

    @Mapping(target = "type.id", source = "typeId")
    User toEntity(UserEditDto dto);
}

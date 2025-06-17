package kg.edu.mathbilim.mapper.user;

import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);

    UserDto toDto(User user);
}

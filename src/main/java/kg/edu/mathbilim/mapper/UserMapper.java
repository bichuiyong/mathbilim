package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);

    UserDto toDto(User user);
}

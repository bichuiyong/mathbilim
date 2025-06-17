package kg.edu.mathbilim.mapper.user;

import kg.edu.mathbilim.dto.user.user_type.UserTypeDto;
import kg.edu.mathbilim.model.user.user_type.UserType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {
    UserType toEntity(UserTypeDto dto);

    UserTypeDto toDto(UserType user);
}

package kg.edu.mathbilim.mapper.reference.user_type;

import kg.edu.mathbilim.dto.reference.user_type.UserTypeDto;
import kg.edu.mathbilim.model.reference.user_type.UserType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {
    UserType toEntity(UserTypeDto dto);

    UserTypeDto toDto(UserType user);
}

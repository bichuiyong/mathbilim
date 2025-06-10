package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.UserTypeDto;
import kg.edu.mathbilim.model.UserType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {
    UserType toEntity(UserTypeDto dto);

    UserTypeDto toDto(UserType user);
}

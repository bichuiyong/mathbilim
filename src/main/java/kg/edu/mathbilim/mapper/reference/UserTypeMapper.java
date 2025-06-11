package kg.edu.mathbilim.mapper.reference;

import kg.edu.mathbilim.dto.reference.UserTypeDto;
import kg.edu.mathbilim.model.reference.UserType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {
    UserType toEntity(UserTypeDto dto);

    UserTypeDto toDto(UserType user);
}

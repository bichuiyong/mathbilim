package kg.edu.mathbilim.mapper.reference.types;

import kg.edu.mathbilim.dto.reference.types.UserTypeDto;
import kg.edu.mathbilim.model.reference.types.UserType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {
    UserType toEntity(UserTypeDto dto);

    UserTypeDto toDto(UserType entity);
}

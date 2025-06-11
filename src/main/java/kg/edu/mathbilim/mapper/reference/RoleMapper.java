package kg.edu.mathbilim.mapper.reference;

import kg.edu.mathbilim.dto.reference.RoleDto;
import kg.edu.mathbilim.model.reference.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto roleDto);

    RoleDto toDto(Role role);
}

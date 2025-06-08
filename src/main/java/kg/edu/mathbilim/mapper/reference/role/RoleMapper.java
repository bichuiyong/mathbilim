package kg.edu.mathbilim.mapper.reference.role;

import kg.edu.mathbilim.dto.reference.role.RoleDto;
import kg.edu.mathbilim.model.reference.role.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto roleDto);

    RoleDto toDto(Role role);
}

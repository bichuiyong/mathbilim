package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.RoleDto;
import kg.edu.mathbilim.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto roleDto);

    RoleDto toDto(Role role);
}

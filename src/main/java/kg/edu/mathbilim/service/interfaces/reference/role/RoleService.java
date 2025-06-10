package kg.edu.mathbilim.service.interfaces.reference.role;

import kg.edu.mathbilim.dto.reference.role.RoleDto;
import kg.edu.mathbilim.model.reference.role.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String name);

    List<RoleDto> getAllRoles();
}

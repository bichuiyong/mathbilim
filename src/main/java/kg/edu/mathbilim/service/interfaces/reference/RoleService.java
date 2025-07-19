package kg.edu.mathbilim.service.interfaces.reference;

import kg.edu.mathbilim.dto.reference.RoleDto;
import kg.edu.mathbilim.model.reference.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String name);

    Role getRoleById(Integer id);

    List<RoleDto> getAllRoles();
}

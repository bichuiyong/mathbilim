package kg.edu.mathbilim.service.impl.reference.role;

import kg.edu.mathbilim.dto.reference.role.RoleDto;
import kg.edu.mathbilim.exception.nsee.RoleNotFoundException;
import kg.edu.mathbilim.mapper.reference.role.RoleMapper;
import kg.edu.mathbilim.model.reference.role.Role;
import kg.edu.mathbilim.repository.reference.role.RoleRepository;
import kg.edu.mathbilim.service.interfaces.reference.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(RoleNotFoundException::new);

    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toDto).collect(Collectors.toList());
    }
}

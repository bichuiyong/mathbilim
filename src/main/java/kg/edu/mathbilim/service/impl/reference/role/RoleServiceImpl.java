package kg.edu.mathbilim.service.impl.reference.role;

import kg.edu.mathbilim.exception.nsee.RoleNotFoundException;
import kg.edu.mathbilim.model.reference.role.Role;
import kg.edu.mathbilim.repository.reference.role.RoleRepository;
import kg.edu.mathbilim.service.interfaces.reference.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(RoleNotFoundException::new);

    }
}

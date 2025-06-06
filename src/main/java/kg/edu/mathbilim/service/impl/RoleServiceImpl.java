package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.exception.RoleNotFoundException;
import kg.edu.mathbilim.model.Role;
import kg.edu.mathbilim.repository.RoleRepository;
import kg.edu.mathbilim.service.interfaces.RoleService;
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

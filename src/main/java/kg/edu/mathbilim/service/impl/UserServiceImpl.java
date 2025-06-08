package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.exception.UserAlreadyExistException;
import kg.edu.mathbilim.mapper.UserMapper;
import kg.edu.mathbilim.model.Role;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.model.UserType;
import kg.edu.mathbilim.repository.UserRepository;
import kg.edu.mathbilim.service.interfaces.RoleService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.UserTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserTypeService userTypeService;

    @Override
    public void createUser(UserDto userDto) {
        log.info("Creating user with email: {}", userDto.getEmail());
        User user = userMapper.toEntity(userDto);
        Role role = roleService.getRoleByName("USER");
        UserType userType = userTypeService.getById(userDto.getType().getId());

        user.setRole(role);
        user.setType(userType);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user = userRepository.saveAndFlush(user);
        log.info("Created user with id: {}", user.getId());
    }

    @Override
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toDto(user);
    }
}

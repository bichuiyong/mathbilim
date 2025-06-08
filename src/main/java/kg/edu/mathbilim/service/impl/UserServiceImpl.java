package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.exception.nsee.UserNotFoundException;
import kg.edu.mathbilim.dto.UserEditDto;
import kg.edu.mathbilim.mapper.UserMapper;
import kg.edu.mathbilim.model.reference.role.Role;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.model.reference.types.UserType;
import kg.edu.mathbilim.repository.UserRepository;
import kg.edu.mathbilim.service.interfaces.reference.role.RoleService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.reference.types.UserTypeService;
import kg.edu.mathbilim.util.PaginationUtil;
import kg.edu.mathbilim.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserTypeService userTypeService;

    private User getEntityById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDto getDtoById(Long id) {
        return userMapper.toDto(getEntityById(id));
    }

    @Override
    public void createUser(UserDto userDto) {
        log.info("Creating user with email: {}", userDto.getEmail());
        User user = userMapper.toEntity(userDto);
        Role role = roleService.getRoleByName("USER");
        UserType userType = userTypeService.getById(userDto.getType().getId());

        user.setRole(role);
        user.setType(userType);
        user.setName(StringUtil.normalizeField(userDto.getName(), true));
        user.setSurname(StringUtil.normalizeField(userDto.getSurname(), true));
        user.setEmail(StringUtil.normalizeField(userDto.getEmail(), false));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user = userRepository.saveAndFlush(user);
        log.info("Created user with id: {}", user.getId());
    }


    @Override
    public void edit(UserEditDto userDto, String email) {
        User user = getEntityByEmail(email);
        user.setName(StringUtil.normalizeField(userDto.getName(), true));
        user.setSurname(StringUtil.normalizeField(userDto.getSurname(), true));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    @Override
    public Page<UserDto> getUserPage(String query, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return getPage(() -> userRepository.findAll(pageable));
        }
        return getPage(() -> userRepository.findByQuery(query, pageable));
    }

    @Transactional
    @Override
    public void toggleUserBlocking(Long userId) {
        User user = getEntityById(userId);
        user.setEnabled(!user.getEnabled().equals(Boolean.TRUE));
        userRepository.save(user);
    }


    private Page<UserDto> getPage(Supplier<Page<User>> supplier, String notFoundMessage) {
        Page<User> userPage = supplier.get();
        if (userPage.isEmpty()) {
            throw new UserNotFoundException(notFoundMessage);
        }
        log.info("Получено {} пользователей на странице", userPage.getSize());
        return userPage.map(userMapper::toDto);
    }

    private Page<UserDto> getPage(Supplier<Page<User>> supplier) {
        return getPage(supplier, "Пользователи не были найдены");
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private User getEntityByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + "not found"));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userMapper.toDto(getEntityByEmail(email));
    }
}

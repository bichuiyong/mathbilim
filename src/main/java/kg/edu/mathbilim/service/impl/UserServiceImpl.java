package kg.edu.mathbilim.service.impl;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.exception.nsee.UserNotFoundException;
import kg.edu.mathbilim.dto.user.UserEditDto;
import kg.edu.mathbilim.mapper.user.UserMapper;
import kg.edu.mathbilim.model.user.UserType;
import kg.edu.mathbilim.model.reference.Role;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.user.UserRepository;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeService;
import kg.edu.mathbilim.service.interfaces.reference.RoleService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.util.UrlUtil;
import kg.edu.mathbilim.util.PaginationUtil;
import kg.edu.mathbilim.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;
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
    private final EmailServiceImpl emailService;

    @Override
    @Transactional
    public void createTelegramUser(Long userId, String name, String surname) {
        User user = User.builder()
                .name(name)
                .email("telegram_" + userId + "@notEmail.com")
                .role(roleService.getRoleByName("USER"))
                .password(passwordEncoder.encode("telegram"+userId+"password"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .surname(surname)
                .telegramId(userId)
                .enabled(true)
                .isEmailVerified(false)
                .build();
        userRepository.save(user);
    }

    @Override
    public boolean existsTelegramUser(String userId) {
        Long telegramId = Long.parseLong(userId);
        return userRepository.existsByTelegramId(telegramId);
    }

    @Override
    public User findByTelegramId(String telegramId) {
        Long userId = Long.parseLong(telegramId);
        return userRepository.findByTelegramId(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getEntityById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDto getDtoById(Long id) {
        return userMapper.toDto(getEntityById(id));
    }


    @Override
    public void createUser(UserDto userDto, HttpServletRequest request) {
        log.info("Creating user with email: {}", userDto.getEmail());
        User user = userMapper.toEntity(userDto);
        Role role = roleService.getRoleByName("USER");
        if (user.getRole() != null) {
            role = roleService.getRoleByName(user.getRole().getName());
        }

        user.setRole(role);
        user.setName(StringUtil.normalizeField(userDto.getName(), true));
        user.setSurname(StringUtil.normalizeField(userDto.getSurname(), true));
        user.setEmail(StringUtil.normalizeField(userDto.getEmail(), false));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setIsEmailVerified(false);

        user = userRepository.saveAndFlush(user);
        log.info("Created user with id: {}", user.getId());

        try {
            generateEmailVerificationToken(request, user.getEmail());
            log.info("Verification email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send verification email to: {}", user.getEmail(), e);
        }
    }

    @Override
    public void edit(UserEditDto userDto, String email) {
        User user = getEntityByEmail(email);
        user.setName(StringUtil.normalizeField(userDto.getName(), true));
        user.setSurname(StringUtil.normalizeField(userDto.getSurname(), true));
        user.setType(userTypeService.getUserTypeEntity(userDto.getTypeId()));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
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
    public UserDto getAuthUser() {
        return userMapper.toDto(getAuthUserEntity());
    }

    @Override
    public User getAuthUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Auth object: {}", authentication);

        if (authentication == null) {
            log.error("Authentication is null");
            throw new NoSuchElementException("user not authorized");
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            log.error("Authentication is anonymous");
            throw new IllegalArgumentException("user not authorized");
        }

        String email = authentication.getName();
        return getEntityByEmail(email);
    }

    @Override
    public Long getAuthId() {
        return getAuthUser().getId();
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

    @Override
    public void setUserType(String email, Integer userTypeId) {
        User user = getEntityByEmail(email);

        if (user.getType() != null) {
            throw new IllegalStateException("User type already set for user: " + email);
        }

        UserType userType = userTypeService.getUserTypeEntity(userTypeId);

        user.setType(userType);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public void updateUser(UserEditDto userDto, Long userId) {
        User user = getEntityById(userId);
        user.setName(StringUtil.normalizeField(userDto.getName(), true));
        user.setSurname(StringUtil.normalizeField(userDto.getSurname(), true));
        user.setRole(roleService.getRoleByName(userDto.getRole().getName().toUpperCase()));
        user.setType(userTypeService.getUserTypeEntity(userDto.getTypeId()));
        userRepository.saveAndFlush(user);
    }

    private void updateResetPasswordToken(String email, String token) {
        User user = getEntityByEmail(email);

        user.setResetPasswordToken(token);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void makeResetPasswordToken(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(email, token);
        String resetPasswordLink = UrlUtil.getSiteURL(request) + "/auth/reset_password?token=" + token;
        emailService.sendEmail(email, resetPasswordLink);
    }


    @Override
    public UserDto getUserByResetPasswordToken(String token) {
        User user = userRepository.findUserByResetPasswordToken(token)
                .orElseThrow(UserNotFoundException::new);

        return userMapper.toDto(user);
    }

    @Override
    public UserDto createOAuthUser(UserDto userDto) {
        if (existsByEmail(userDto.getEmail())) {
            throw new IllegalStateException("Пользователь уже существует: " + userDto.getEmail());
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .role(roleService.getRoleByName("USER"))
                .isEmailVerified(true)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public void updatePassword(Long userId, String password) {
        User user = getEntityById(userId);

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void generateEmailVerificationToken(HttpServletRequest request, String email) throws MessagingException, UnsupportedEncodingException {
        User user = getEntityByEmail(email);

        String token = UUID.randomUUID().toString();

        user.setEmailVerificationToken(token);
        user.setIsEmailVerified(false);
        userRepository.saveAndFlush(user);

        String verificationLink = UrlUtil.getSiteURL(request) + "/auth/verify-email?token=" + token;
        emailService.sendVerificationEmail(email, verificationLink);
    }

    @Override
    public boolean verifyEmail(String token) {
        User user = getEntityByEmailVerificationToken(token);

        user.setIsEmailVerified(true);
        user.setEmailVerificationToken(null);
        userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    public void resendVerificationEmail(HttpServletRequest request, String email)
            throws MessagingException, UnsupportedEncodingException {
        User user = getEntityByEmail(email);

        if (Boolean.TRUE.equals(user.getIsEmailVerified())) {
            throw new IllegalStateException("Email уже верифицирован");
        }

        String token = UUID.randomUUID().toString();

        user.setEmailVerificationToken(token);
        userRepository.saveAndFlush(user);

        String verificationLink = UrlUtil.getSiteURL(request) + "/auth/verify-email?token=" + token;
        emailService.sendVerificationEmail(email, verificationLink);
    }

    private User getEntityByEmailVerificationToken(String token) {
        return userRepository.findByEmailVerificationToken(token)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDto getUserByEmailVerificationToken(String token) {
        return userMapper.toDto(getEntityByEmailVerificationToken(token));
    }

    @Override
    public boolean isEmailVerified(String email) {
        User user = getEntityByEmail(email);

        return Boolean.TRUE.equals(user.getIsEmailVerified());
    }
}

package kg.edu.mathbilim.service.impl;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.dto.UserEditByAdminDto;
import kg.edu.mathbilim.exception.nsee.UserNotFoundException;
import kg.edu.mathbilim.dto.UserEditDto;
import kg.edu.mathbilim.mapper.UserMapper;
import kg.edu.mathbilim.model.reference.UserType;
import kg.edu.mathbilim.model.reference.Role;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.repository.UserRepository;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeService;
import kg.edu.mathbilim.service.interfaces.reference.RoleService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.util.CommonUtilities;
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
import java.time.Instant;
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
        user.setType(userTypeService.findById(userDto.getTypeId()));
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

        UserType userType = userTypeService.findById(userTypeId);

        user.setType(userType);
        user.setUpdatedAt(Instant.now());

        userRepository.save(user);
    }

    @Override
    public void updateUser(UserEditByAdminDto userDto, Long userId) {
        User user = getEntityById(userId);
        user.setName(StringUtil.normalizeField(userDto.getName(), true));
        user.setSurname(StringUtil.normalizeField(userDto.getSurname(), true));
        user.setRole(roleService.getRoleByName(userDto.getRole().getName().toUpperCase()));
        user.setType(userTypeService.findById(userDto.getType().getId()));
        userRepository.saveAndFlush(user);
    }

    private void updateResetPasswordToken(String email, String token) {
        User user = userRepository.findByEmail(email).
                orElseThrow(UserNotFoundException::new);

        user.setResetPasswordToken(token);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void makeResetPasswordToken(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(email, token);
        String resetPasswordLink = CommonUtilities.getSiteURL(request) + "/auth/reset_password?token=" + token;
        emailService.sendEmail(email, resetPasswordLink);
    }


    @Override
    public UserDto getUserByResetPasswordToken(String token) {
        User user = userRepository.findUserByResetPasswordToken(token)
                .orElseThrow(UserNotFoundException::new);

        return userMapper.toDto(user);
    }


    @Override
    public void updatePassword(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.saveAndFlush(user);
    }



    @Override
    public void generateEmailVerificationToken(HttpServletRequest request, String email) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        String token = UUID.randomUUID().toString();

        user.setEmailVerificationToken(token);
        user.setIsEmailVerified(false);
        userRepository.saveAndFlush(user);

        String verificationLink = CommonUtilities.getSiteURL(request) + "/auth/verify-email?token=" + token;
        emailService.sendVerificationEmail(email, verificationLink);
    }

    @Override
    public boolean verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElse(null);

        if (user == null) {
            return false;
        }

        user.setIsEmailVerified(true);
        user.setEmailVerificationToken(null);
        userRepository.saveAndFlush(user);

        return true;
    }

    @Override
    public void resendVerificationEmail(HttpServletRequest request, String email)
            throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (Boolean.TRUE.equals(user.getIsEmailVerified())) {
            throw new IllegalStateException("Email уже верифицирован");
        }

        String token = UUID.randomUUID().toString();

        user.setEmailVerificationToken(token);
        userRepository.saveAndFlush(user);

        String verificationLink = CommonUtilities.getSiteURL(request) + "/auth/verify-email?token=" + token;
        emailService.sendVerificationEmail(email, verificationLink);
    }

    @Override
    public UserDto getUserByEmailVerificationToken(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(UserNotFoundException::new);

        return userMapper.toDto(user);
    }

    @Override
    public boolean isEmailVerified(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return Boolean.TRUE.equals(user.getIsEmailVerified());
    }


}

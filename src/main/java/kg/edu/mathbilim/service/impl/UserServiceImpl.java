package kg.edu.mathbilim.service.impl;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.edu.mathbilim.dto.user.PublicUserDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.dto.user.UserEmailDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.exception.nsee.UserNotFoundException;
import kg.edu.mathbilim.dto.user.UserEditDto;
import kg.edu.mathbilim.mapper.user.UserMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.user.UserType;
import kg.edu.mathbilim.model.reference.Role;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.user.UserRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.*;

import static kg.edu.mathbilim.util.PaginationUtil.getPage;

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
    private final FileService fileService;


    @Override
    public boolean userEmailIsNotReal(String email) {
        return email.endsWith("notEmail.com");
    }

    @Override
    public void newUserEmail(String email, UserEmailDto userEmailDto, HttpServletRequest request) {
        UserType userType = userTypeService.getUserTypeEntity(userEmailDto.getType());
        User user = userRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
        user.setEmail(userEmailDto.getEmail());
        user.setType(userType);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        try {
            generateEmailVerificationToken(request, user.getEmail());
            log.info("Verification email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send verification email to: {}", user.getEmail(), e);
        }
    }

    @Override
    @Transactional
    public void createTelegramUser(Long userId, String name, String surname) {
        User user = User.builder()
                .name(name)
                .email("telegram_" + userId + "@notEmail.com")
                .role(roleService.getRoleByName("USER"))
                .password(passwordEncoder.encode("telegram" + userId + "password"))
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
    public UserDto getByTelegramId(String telegramId) {
        Long userId = Long.parseLong(telegramId);
        User user = userRepository.findByTelegramId(userId).orElseThrow(UserNotFoundException::new);

        return userMapper.toDto(user);
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
    public void createUserFromAdmin(UserDto userDto, HttpServletRequest request) {
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
        user.setIsEmailVerified(true);

        user = userRepository.saveAndFlush(user);
        log.info("Created user with id: {}", user.getId());
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
    public void edit(UserEditDto userDto) {
        User user = getEntityById(userDto.getId());
        user.setName(StringUtil.normalizeField(userDto.getName(), true));
        user.setSurname(StringUtil.normalizeField(userDto.getSurname(), true));
        if (userDto.getTypeId() != null) {
            user.setType(userTypeService.getUserTypeEntity(userDto.getTypeId()));
        }
//        user.setRole(roleService.getRoleById(userDto.getRole().getId()));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(Long id) {
        checkBeforeUpdate(id);
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
        User user = getAuthUserEntity();

        if (query == null || query.isEmpty()) {
            return getPage(() -> userRepository.findAllExceptCurrent(user.getId(), pageable), userMapper::toDto);
        }

        return getPage(() -> userRepository.findByQueryExceptCurrent(query, user.getId(), pageable), userMapper::toDto);
    }

    @Transactional
    @Override
    public void toggleUserBlocking(Long userId) {
        checkBeforeUpdate(userId);
        User user = getEntityById(userId);
        user.setEnabled(!user.getEnabled().equals(Boolean.TRUE));
        userRepository.save(user);
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

    @Override
    public User getEntityByEmail(String email) {
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
        String changingRole = userDto.getRole().getName();
        checkBeforeUpdate(changingRole,userId);
        User user = getEntityById(userId);
        user.setName(StringUtil.normalizeField(userDto.getName(), true));
        user.setSurname(StringUtil.normalizeField(userDto.getSurname(), true));
        user.setRole(roleService.getRoleByName(userDto.getRole().getName().toUpperCase()));
        user.setType(userTypeService.getUserTypeEntity(userDto.getTypeId()));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void setUserAvatar(Long userId, MultipartFile file) {
        File avatar = fileService.uploadAvatarReturnEntity(file);
        User user = getEntityById(userId);
        user.setAvatar(avatar);
        userRepository.saveAndFlush(user);
        log.info("Установлен автар пользователя");
    }

    @Override
    public UserEditDto getEditUserById(Long id) {
        User user = getEntityById(id);
        return userMapper.toEditDto(user);
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


    @Override
    public void registerChatId(Long userId, Long chatId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setTelegramId(chatId);
        userRepository.saveAndFlush(user);
    }

    @Override
    public boolean hasChatId(Long chatId) {
        log.debug("🔍 Безопасная проверка наличия chatId: {}", chatId);

        try {
            Optional<User> user = userRepository.findByTelegramId(chatId);

            if (user.isEmpty()) {
                log.debug("❌ Пользователь с telegramId={} не найден", chatId);
                return false;
            }

            User foundUser = user.get();
            Long telegramId = foundUser.getTelegramId();

            if (telegramId == null) {
                log.warn("⚠️ У пользователя id={} telegramId равен null", foundUser.getId());
                return false;
            }

            boolean matches = telegramId.equals(chatId);
            log.debug("✅ Пользователь найден: id={}, telegramId={}, совпадает={}",
                    foundUser.getId(), telegramId, matches);

            return matches;

        } catch (Exception e) {
            log.error("❌ Ошибка при проверке chatId: {}", chatId, e);
            return false;
        }
    }

    @Override
    public List<Long> getSubscribedChatIds() {
        return userRepository.findAllBySubscribedTrueAndTelegramIdIsNotNull()
                .stream()
                .map(User::getTelegramId)
                .toList();
    }


    @Override
    public void unsubscribe(Long chatId) {
        User user = userRepository.findByTelegramId(chatId).orElseThrow(UserNotFoundException::new);
        user.setSubscribed(false);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void subscribe(Long chatId) {
        User user = userRepository.findByTelegramId(chatId).orElseThrow(UserNotFoundException::new);
        user.setSubscribed(true);
        userRepository.saveAndFlush(user);
    }

    @Override
    public boolean isSubscribed(Long chatId) {
        User user = userRepository.findByTelegramId(chatId).orElseThrow(UserNotFoundException::new);
        return user != null && Boolean.TRUE.equals(user.getSubscribed());
    }

    @Override
    public void changePassword(String password, String newPassword) throws IllegalStateException {
        UserDto authUser = getAuthUser();
        if (passwordEncoder.matches(password, authUser.getPassword())) {
            authUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(userMapper.toEntity(authUser));
        } else {
            throw new IllegalArgumentException("you previous password is incorrect");
        }
    }

    @Override
    public boolean checkOldPassword(String oldPassword) {
        UserDto authUser = getAuthUser();
        String encodedPassword = passwordEncoder.encode(oldPassword);
        return authUser.getPassword().equals(encodedPassword);
    }

    private void checkBeforeUpdate(String changingRole,Long userId) {
        UserDto authUser = getAuthUser();
        User updatingUser = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        String authRole = authUser.getRole().getName();
        String targetRole = updatingUser.getRole().getName();

        if (Objects.equals(userId, authUser.getId())) {
            throw new IllegalStateException("User cannot update themselves.");
        }

        if (authRole.equals("SUPER_ADMIN")) {
            if(changingRole.equals("SUPER_ADMIN")) {
                throw new IllegalStateException("SUPER_ADMIN couldn't make another superadmin");
            }
            return;
        }

        if (authRole.equals("ADMIN")) {
            if (targetRole.equals("SUPER_ADMIN") || targetRole.equals("ADMIN")) {
                throw new IllegalStateException("ADMIN cannot update SUPER_ADMIN or another ADMIN.");
            }
            return;
        }

        throw new IllegalStateException(authRole + " is not allowed to update users.");
    }
    private void checkBeforeUpdate(Long userId) {
        UserDto authUser = getAuthUser();
        User updatingUser = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        String authRole = authUser.getRole().getName();
        String targetRole = updatingUser.getRole().getName();

        if (Objects.equals(userId, authUser.getId())) {
            throw new IllegalStateException("User cannot update themselves.");
        }

        if (authRole.equals("SUPER_ADMIN")) {
            return;
        }

        if (authRole.equals("ADMIN")) {
            if (targetRole.equals("SUPER_ADMIN") || targetRole.equals("ADMIN")) {
                throw new IllegalStateException("ADMIN cannot update SUPER_ADMIN or another ADMIN.");
            }
            return;
        }

        throw new IllegalStateException(authRole + " is not allowed to update users.");
    }


    @Override
    public int approvedContentCount(Long userId) {
        return userRepository.countContentByStatus(userId, ContentStatus.APPROVED.getId());
    }

    @Override
    public PublicUserDto getPublicDtoById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return PublicUserDto.builder()
                .id(user.getId())
                .type(user.getType())
                .name(user.getName())
                .surname(user.getSurname())

                .build();
    }

    @Override
    public int pendingContentCount(Long userId) {
        return userRepository.countContentByStatus(userId, ContentStatus.PENDING_REVIEW.getId());
    }

    @Override
    public int totalContentCount(Long userId) {
        return userRepository.totalContent(userId);
    }


}

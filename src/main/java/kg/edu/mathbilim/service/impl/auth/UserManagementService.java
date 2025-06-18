package kg.edu.mathbilim.service.impl.auth;

import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserService userService;

    @Transactional
    public UserCreationResult createOrUpdateOAuthUser(String email, String fullName) {
        try {
            UserDto existingUser = userService.getUserByEmail(email);

            if (existingUser != null) {
                boolean needsTypeSelection = existingUser.getType() == null;
                return new UserCreationResult(existingUser, needsTypeSelection, false);
            }

        } catch (Exception e) {
            log.debug("User not found, will create new: email={}", email);
        }

        UserDto newUser = createNewOAuthUser(email, fullName);
        return new UserCreationResult(newUser, true, false);
    }

    private UserDto createNewOAuthUser(String email, String fullName) {
        try {
            UserDto userDto = new UserDto();
            userDto.setEmail(email);

            if (fullName != null && !fullName.trim().isEmpty()) {
                String[] nameParts = fullName.trim().split("\\s+");
                userDto.setName(nameParts[0]);
                if (nameParts.length > 1) {
                    userDto.setSurname(String.join(" ",
                            java.util.Arrays.copyOfRange(nameParts, 1, nameParts.length)));
                }
            } else {
                String emailPrefix = email.substring(0, email.indexOf('@'));
                userDto.setName(emailPrefix);
            }

            return userService.createOAuthUser(userDto);

        } catch (Exception e) {
            log.error("Failed to create OAuth user: email={}", email, e);
            throw new RuntimeException("Ошибка создания пользователя", e);
        }
    }

    public record UserCreationResult(UserDto user, boolean needsTypeSelection, boolean wasUpdated) {}
}

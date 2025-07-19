package kg.edu.mathbilim.service.impl.auth;

import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Loading user by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + email));

        validateUser(user);
        return new CustomUserDetails(user);
    }

    private void validateUser(User user) {
        if (!Boolean.TRUE.equals(user.getIsEmailVerified())) {
            throw new DisabledException("Email не подтвержден. Проверьте почту и перейдите по ссылке подтверждения.");
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new DisabledException("Аккаунт заблокирован. Обратитесь к администратору.");
        }
    }

    public UserDetails loadUserByTelegram(String telegramId) throws UsernameNotFoundException {
        Long userId = Long.parseLong(telegramId);
        User user = userRepository.findByTelegramId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(telegramId));

        return new CustomUserDetails(user);
    }
}
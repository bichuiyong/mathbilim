package kg.edu.mathbilim.service.impl.auth;

import kg.edu.mathbilim.model.reference.Authority;
import kg.edu.mathbilim.model.reference.Role;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRole())
        );
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
        User user = userRepository.findByTelegramId(userId).orElseThrow(() -> new UsernameNotFoundException(telegramId));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRole())

        );
    }

    private Collection<GrantedAuthority> getAuthorities(Role role) {
        List<String> privileges = getPrivileges(role);
        return privileges.stream()
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
    }

    private List<String> getPrivileges(Role role) {
        List<String> privileges = new ArrayList<>();
        privileges.add(role.getName());

        role.getAuthorities().stream()
                .map(Authority::getName)
                .forEach(privileges::add);

        return privileges;
    }
}

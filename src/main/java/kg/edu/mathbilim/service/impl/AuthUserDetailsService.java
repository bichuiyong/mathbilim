package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.model.reference.role.Authority;
import kg.edu.mathbilim.model.reference.role.Role;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.repository.UserRepository;
import kg.edu.mathbilim.repository.reference.role.RoleRepository;
import kg.edu.mathbilim.service.interfaces.reference.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        if (!Boolean.TRUE.equals(user.getIsEmailVerified())) {
            throw new DisabledException("Email не подтвержден. Проверьте почту и перейдите по ссылке подтверждения.");
        }

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

    private Collection<GrantedAuthority> getAuthorities(Role roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private List<String> getPrivileges(Role roles) {
        List<String> privileges = new ArrayList<>();
        privileges.add(roles.getName());
        List<Authority> collection = new ArrayList<>(roles.getAuthorities());
        for (Authority authority : collection) {
            privileges.add(authority.getName());
        }
        return privileges;
    }

    public Boolean processOAuthPostLogin(String email, String fullName) {
        var existUser = userRepository.findByEmail(email);

        boolean isNewUser = false;

        if (existUser.isEmpty()) {
            isNewUser = true;
            Role role = roleService.getRoleByName("USER");

            String firstName = "";
            String lastName = "";

            if (fullName != null && !fullName.trim().isEmpty()) {
                String[] nameParts = fullName.trim().split("\\s+");
                firstName = nameParts[0];
                if (nameParts.length > 1) {
                    lastName = String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length));
                }
            } else {
                firstName = email.split("@")[0];
            }

            User user = User.builder()
                    .email(email)
                    .name(firstName)
                    .surname(lastName)
                    .password(passwordEncoder.encode("qwerty"))
                    .role(role)
                    .isEmailVerified(true)
                    .preferredLanguage("ru")
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .enabled(Boolean.TRUE)
                    .build();

            userRepository.saveAndFlush(user);
        }

        UserDetails userDetails = loadUserByUsername(email);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return isNewUser;
    }
}

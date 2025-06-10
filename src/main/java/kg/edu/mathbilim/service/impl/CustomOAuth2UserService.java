package kg.edu.mathbilim.service.impl;


import kg.edu.mathbilim.model.CustomOAuth2User;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.model.reference.role.Role;
import kg.edu.mathbilim.repository.UserRepository;
import kg.edu.mathbilim.service.interfaces.reference.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserDetailsService authUserDetailsService;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        CustomOAuth2User customUser = new CustomOAuth2User(oAuth2User);
        processOAuthPostLogin(customUser);

        return customUser;
    }

    private void processOAuthPostLogin(CustomOAuth2User customUser) {
        String email = customUser.getEmail();
        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        var existUser = userRepository.findByEmail(email);

        if (existUser.isEmpty()) {
            Role role = roleService.getRoleByName("USER");

            var user = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode("qwerty"))
                    .role(role)
                    .isEmailVerified(false)
                    .preferredLanguage("ru")
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .enabled(Boolean.TRUE)
                    .build();

            userRepository.save(user);
        }
    }
}
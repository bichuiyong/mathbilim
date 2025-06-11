package kg.edu.mathbilim.config;

import kg.edu.mathbilim.service.impl.auth.AuthUserDetailsService;
import kg.edu.mathbilim.service.impl.auth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final AuthUserDetailsService userService;
    private final CustomOAuth2UserService oauthUserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService, UserDetailsService userDetailsService) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

                .oauth2Login(oauth -> oauth
                        .loginPage("/auth/login")
                        .userInfoEndpoint(userConfig -> userConfig
                                .userService(oauthUserService))
                        .successHandler((request, response, authentication) -> {
                            String email = null;
                            String fullName = null;

                            if (authentication.getPrincipal() instanceof OidcUser) {
                                OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
                                email = oidcUser.getEmail();
                                fullName = oidcUser.getFullName();
                            }
                            else if (authentication.getPrincipal() instanceof OAuth2User) {
                                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                                email = oauth2User.getAttribute("email");
                                fullName = oauth2User.getAttribute("name");
                            }

                            if (email != null) {
                                boolean isNewUser = userService.processOAuthPostLogin(email, fullName);

                                if (isNewUser) {
                                    response.sendRedirect("/auth/select-user-type");
                                } else {
                                    response.sendRedirect("/");
                                }
                            } else {
                                response.sendRedirect("/auth/login?error=true");
                            }
                        })
                )

                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("email")
                        .failureUrl("/auth/login?error=true")
                        .defaultSuccessUrl("/profile", true)
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll())

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/posts/create/**", "/organizations/create/**").authenticated()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/users/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll());

        return http.build();
    }
}


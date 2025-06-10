package kg.edu.mathbilim.config;

import kg.edu.mathbilim.service.impl.AuthUserDetailsService;
import kg.edu.mathbilim.service.impl.CustomOAuth2UserService;
import kg.edu.mathbilim.model.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final AuthUserDetailsService authUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService, UserDetailsService userDetailsService) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

                .oauth2Login(oauth -> oauth
                        .loginPage("/auth/login")
                        .userInfoEndpoint(userConfig -> userConfig
                                .userService(customOAuth2UserService))
                        .defaultSuccessUrl("/", true))

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
                        .anyRequest().permitAll());

        return http.build();
    }
}


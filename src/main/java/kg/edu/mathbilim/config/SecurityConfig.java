package kg.edu.mathbilim.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println(new BCryptPasswordEncoder().encode("password"));
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout((logout) -> logout.logoutUrl("/logout"))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("images/**").fullyAuthenticated()
                        .anyRequest().permitAll());

        return http.build();
    }
}


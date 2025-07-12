package kg.edu.mathbilim.config;

import com.sun.research.ws.wadl.HTTPMethods;
import kg.edu.mathbilim.service.impl.auth.CustomOAuth2UserService;
import kg.edu.mathbilim.service.impl.auth.OAuth2LoginSuccessHandler;
import kg.edu.mathbilim.service.impl.auth.UserEmailHandler;
import kg.edu.mathbilim.service.impl.auth.UserTypeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService oauthUserService;
    private final OAuth2LoginSuccessHandler oauthSuccessHandler;
    private final UserTypeHandler userTypeHandler;
    private final UserEmailHandler userEmailHandler;

    @Value("${logging.app.remember-me.key:#{T(java.util.UUID).randomUUID().toString()}}")
    private String rememberMeKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterAfter(userEmailHandler, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(userTypeHandler, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/auth/telegram/callback")
                        .ignoringRequestMatchers("/api/users", "/api/users/**") // временно
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

                .httpBasic(Customizer.withDefaults())


                .oauth2Login(oauth -> oauth
                        .loginPage("/auth/login")
                        .userInfoEndpoint(userConfig -> userConfig
                                .userService(oauthUserService))
                        .successHandler(oauthSuccessHandler)
                        .failureUrl("/auth/login?error=oauth_failed")
                )

                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .failureUrl("/auth/login?error=true")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .rememberMe(rememberMe -> rememberMe
                        .key(rememberMeKey)
                        .tokenValiditySeconds(86400*14))

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/admin/**",
                                "/news/create/**")
                        .hasAuthority("ADMIN")

                        .requestMatchers(
                                "/posts/create/**",
                                "/organizations/create/**",
                                "/books/create/**",
                                "/books/update/**",
                                "/profile/**",
                                "/blog/create/**",
                                "/events/create/**",
                                "/users/*"
                        ).authenticated()

                        .requestMatchers( "/notifications/**").authenticated()

                            .requestMatchers(
                                    "/olympiad/create",
                                    "/olympiad/edit"
                            ).hasAnyAuthority("ADMIN","MODER")

                        .requestMatchers(
                                "/auth/**",
                                "/",
                                "/about",
                                "/static/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/error"
                        ).permitAll()
                        .requestMatchers("/api/auth/check").permitAll()

                        .anyRequest().permitAll()
                )

                .build();
    }

}


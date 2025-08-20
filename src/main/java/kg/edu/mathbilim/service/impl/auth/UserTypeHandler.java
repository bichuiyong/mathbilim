package kg.edu.mathbilim.service.impl.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTypeHandler extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/auth/select-user-type") ||
                path.startsWith("/auth") ||
                path.startsWith("/static") ||
                path.startsWith("/css") ||
                path.startsWith("/js") ||
                path.startsWith("/images") ||
                path.startsWith("/error") ||
                path.startsWith("/logout")) {

            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = getUsername(authentication);
            if (username != null) {
                User user = userRepository.findByEmail(username).orElse(null);
            if (user.getRole().getName().equalsIgnoreCase("admin")
                        || user.getRole().getName().equalsIgnoreCase("moder")
            || user.getRole().getName().equalsIgnoreCase("super_admin")) {
                filterChain.doFilter(request, response);
                    return;
                }
                if (user != null && user.getType() == null) {
                    response.sendRedirect("/auth/select-user-type");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getUsername(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else if (principal instanceof String str) {
            return str;
        }
        return null;
    }
}

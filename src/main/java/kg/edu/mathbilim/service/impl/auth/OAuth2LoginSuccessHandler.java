package kg.edu.mathbilim.service.impl.auth;

import kg.edu.mathbilim.service.impl.auth.UserManagementService.UserCreationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserManagementService userManagementService;
    private final UserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        try {
            OAuthUserInfo userInfo = extractUserInfo(authentication);

            if (userInfo.email() == null) {
                response.sendRedirect("/auth/login?error=email_required");
                return;
            }

            UserCreationResult result = userManagementService.createOrUpdateOAuthUser(
                    userInfo.email(),
                    userInfo.fullName()
            );

            authenticateUser(result.user().getEmail());
            String redirectUrl = determineRedirectUrl(result.needsTypeSelection());

            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            response.sendRedirect("/auth/login?error=processing_failed");
        }
    }

    private void authenticateUser(String email) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Successfully authenticated OAuth user: {}", email);
        } catch (Exception e) {
            log.error("Failed to authenticate OAuth user: {}", email, e);
        }
    }

    private OAuthUserInfo extractUserInfo(Authentication authentication) {
        String email = null;
        String fullName = null;

        Object principal = authentication.getPrincipal();

        if (principal instanceof OidcUser oidcUser) {
            email = oidcUser.getEmail();
            fullName = oidcUser.getFullName();
        } else if (principal instanceof OAuth2User oauth2User) {
            email = oauth2User.getAttribute("email");
            fullName = oauth2User.getAttribute("name");
        }

        return new OAuthUserInfo(email, fullName);
    }

    private String determineRedirectUrl(boolean needsTypeSelection) {
        if (needsTypeSelection) {
            return "/auth/select-user-type";
        }
        return "/profile";
    }

    private record OAuthUserInfo(String email, String fullName) {}
}
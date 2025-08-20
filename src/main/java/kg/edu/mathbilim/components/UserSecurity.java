package kg.edu.mathbilim.components;

import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {
    private final UserService userService;

    public boolean isOwner(Authentication principal, Long id) {
        return userService.getDtoById(id).getEmail().equals(principal.getName());

    }
}

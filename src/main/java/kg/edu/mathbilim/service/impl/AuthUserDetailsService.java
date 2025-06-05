package kg.edu.mathbilim.service.impl;



import kg.edu.mathbilim.model.Authority;
import kg.edu.mathbilim.model.Role;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

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

}

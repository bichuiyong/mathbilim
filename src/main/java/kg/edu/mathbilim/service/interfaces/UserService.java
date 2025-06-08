package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.model.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    User getEntityById(Long userId);

    UserDto getDtoById(Long id);

    void createUser(UserDto userDto);

    Page<UserDto> getUserPage(String query, int page, int size, String sortBy, String sortDirection);

    @Transactional
    void toggleUserBlocking(Long userId);

    boolean existsByEmail(String email);

    void deleteUser(Long id);
}

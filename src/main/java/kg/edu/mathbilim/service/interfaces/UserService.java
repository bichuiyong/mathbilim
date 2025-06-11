package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.model.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import kg.edu.mathbilim.dto.UserEditDto;

import java.io.UnsupportedEncodingException;

public interface UserService {

    User getEntityById(Long userId);

    UserDto getDtoById(Long id);

    void createUser(UserDto userDto);

    UserDto getUserByEmail(String email);

    void edit(UserEditDto userDto, String email);

    Page<UserDto> getUserPage(String query, int page, int size, String sortBy, String sortDirection);

    @Transactional
    void toggleUserBlocking(Long userId);

    UserDto getAuthUser();

    User getAuthUserEntity();

    Long getAuthId();

    boolean existsByEmail(String email);

    void deleteUser(Long id);

    void setUserType(String email, Long userTypeId);

    void makeResetPasswordToken(HttpServletRequest request)
            throws
            MessagingException,
            UnsupportedEncodingException;
}

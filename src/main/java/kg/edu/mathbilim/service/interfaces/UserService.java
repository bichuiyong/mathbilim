package kg.edu.mathbilim.service.interfaces;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.dto.user.UserEditByAdminDto;
import kg.edu.mathbilim.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import kg.edu.mathbilim.dto.user.UserEditDto;

import java.io.UnsupportedEncodingException;

public interface UserService {

    User getEntityById(Long userId);

    UserDto getDtoById(Long id);

    UserDto getUserByEmail(String email);

    void createUser(UserDto userDto, HttpServletRequest request);

    void edit(UserEditDto userDto, String email);

    Page<UserDto> getUserPage(String query, int page, int size, String sortBy, String sortDirection);

    @Transactional
    void toggleUserBlocking(Long userId);

    UserDto getAuthUser();

    User getAuthUserEntity();

    Long getAuthId();

    boolean existsByEmail(String email);

    void deleteUser(Long id);

    void setUserType(String email, Integer userTypeId);

    void makeResetPasswordToken(HttpServletRequest request)
            throws
            MessagingException,
            UnsupportedEncodingException;

    UserDto getUserByResetPasswordToken(String token);

    void updatePassword(Long userId, String password);

    void generateEmailVerificationToken(HttpServletRequest request, String email) throws MessagingException, UnsupportedEncodingException;

    boolean verifyEmail(String token);

    void resendVerificationEmail(HttpServletRequest request, String email)
            throws MessagingException, UnsupportedEncodingException;

    UserDto getUserByEmailVerificationToken(String token);

    boolean isEmailVerified(String email);

    void updateUser(UserEditByAdminDto userDto, Long userId);
}

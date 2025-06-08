package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.UserDto;

public interface UserService {
    void createUser(UserDto userDto);

    UserDto getUserByEmail(String email);


    void edit(UserEditDto userDto, String email);

    boolean existsByEmail(String email);
}

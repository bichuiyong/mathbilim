package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.UserTypeDto;
import kg.edu.mathbilim.model.UserType;

import java.util.List;

public interface UserTypeService {
    List<UserTypeDto> getAllTypes();

    UserType getById(Integer id);
}

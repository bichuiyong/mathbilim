package kg.edu.mathbilim.service.interfaces.reference.types;

import kg.edu.mathbilim.dto.reference.types.UserTypeDto;
import kg.edu.mathbilim.model.reference.types.UserType;

import java.util.List;

public interface UserTypeService {
    List<UserTypeDto> getAllTypes();

    UserType getById(Integer id);
}

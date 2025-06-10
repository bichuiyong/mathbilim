package kg.edu.mathbilim.service.interfaces;


import kg.edu.mathbilim.model.UserType;

import java.util.List;

public interface UserTypeService {

    List<UserType> getAll();

    UserType findById(Long id);
}

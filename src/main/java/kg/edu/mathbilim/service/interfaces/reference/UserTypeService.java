package kg.edu.mathbilim.service.interfaces.reference;


import kg.edu.mathbilim.model.reference.UserType;

import java.util.List;

public interface UserTypeService {

    List<UserType> getAll();

    UserType findById(Long id);
}

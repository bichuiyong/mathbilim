package kg.edu.mathbilim.service.interfaces.reference;


import kg.edu.mathbilim.dto.user.UserTypeDto;
import kg.edu.mathbilim.model.user.UserType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserTypeService {

    List<UserTypeDto> getAllUserTypes();

    UserType getUserTypeEntity(Integer id);

    UserTypeDto getUserTypeById(Integer id);

    List<UserTypeDto> getUserTypesByLanguage(String languageCode);

    @Transactional
    UserTypeDto createUserType(UserTypeDto userTypeDto);

    @Transactional
    UserTypeDto updateUserType(Integer id, UserTypeDto userTypeDto);

    @Transactional
    void deleteUserType(Integer id);

    @Transactional
    UserTypeDto addTranslation(Integer userTypeId, String languageCode, String translation);

    @Transactional
    UserTypeDto removeTranslation(Integer userTypeId, String languageCode);
}

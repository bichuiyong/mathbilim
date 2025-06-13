package kg.edu.mathbilim.service.interfaces.reference.user_type;

import kg.edu.mathbilim.dto.user.user_type.UserTypeTranslationDto;
import kg.edu.mathbilim.model.reference.user_type.UserTypeTranslation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserTypeTranslationService {
    List<UserTypeTranslationDto> getTranslationsByUserTypeId(Integer userTypeId);

    UserTypeTranslation getTranslationEntity(Integer userTypeId, String languageCode);

    UserTypeTranslationDto getTranslation(Integer userTypeId, String languageCode);

    List<UserTypeTranslationDto> getTranslationsByLanguage(String languageCode);

    @Transactional
    UserTypeTranslationDto createTranslation(UserTypeTranslationDto dto);

    @Transactional
    UserTypeTranslationDto updateTranslation(Integer userTypeId, String languageCode, String newTranslation);

    @Transactional
    UserTypeTranslationDto upsertTranslation(UserTypeTranslationDto dto);

    void deleteTranslation(Integer userTypeId, String languageCode);

    @Transactional
    void deleteAllTranslationsByUserTypeId(Integer userTypeId);

    boolean existsTranslation(Integer userTypeId, String languageCode);
}

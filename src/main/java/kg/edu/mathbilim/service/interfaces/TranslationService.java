package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.reference.category.CategoryDto;
import kg.edu.mathbilim.dto.reference.user_type.UserTypeDto;

import java.util.List;

public interface TranslationService {
    List<UserTypeDto> getUserTypesByLanguage();

    List<CategoryDto> getCategoriesByLanguage();
}

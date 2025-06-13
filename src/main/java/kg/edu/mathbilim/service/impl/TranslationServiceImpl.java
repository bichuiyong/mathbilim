package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.reference.category.CategoryDto;
import kg.edu.mathbilim.dto.reference.user_type.UserTypeDto;
import kg.edu.mathbilim.service.interfaces.TranslationService;
import kg.edu.mathbilim.service.interfaces.reference.category.CategoryService;
import kg.edu.mathbilim.service.interfaces.reference.user_type.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {
    private final UserTypeService userTypeService;
    private final CategoryService categoryService;


    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }


    @Override
    public List<UserTypeDto> getUserTypesByLanguage() {
        return userTypeService.getUserTypesByLanguage(getCurrentLocale().getLanguage());
    }

    @Override
    public List<CategoryDto> getCategoriesByLanguage() {
        return categoryService.getCategoriesByLanguage(getCurrentLocale().getLanguage());
    }


}

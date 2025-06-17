package kg.edu.mathbilim.service.interfaces.reference;

import kg.edu.mathbilim.dto.reference.category.CategoryTranslationDto;
import kg.edu.mathbilim.model.reference.category.CategoryTranslation;

import java.util.List;

public interface CategoryTranslationService {
    List<CategoryTranslationDto> getTranslationsByCategoryId(Integer categoryId);

    CategoryTranslation getTranslationEntity(Integer categoryId, String languageCode);

    CategoryTranslationDto getTranslation(Integer categoryId, String languageCode);

    List<CategoryTranslationDto> getTranslationsByLanguage(String languageCode);

    CategoryTranslationDto createTranslation(CategoryTranslationDto dto);

    CategoryTranslationDto updateTranslation(Integer categoryId, String languageCode, String newTranslation);

    CategoryTranslationDto upsertTranslation(CategoryTranslationDto dto);

    void deleteTranslation(Integer categoryId, String languageCode);

    void deleteAllTranslationsByCategoryId(Integer categoryId);

    boolean existsTranslation(Integer categoryId, String languageCode);
}

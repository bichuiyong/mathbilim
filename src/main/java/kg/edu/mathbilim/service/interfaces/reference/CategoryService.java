package kg.edu.mathbilim.service.interfaces.reference;


import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.model.reference.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();

    Category getCategoryEntity(Integer id);

    CategoryDto getCategoryById(Integer id);

    List<CategoryDto> getCategoriesByLanguage(String languageCode);

    @Transactional
    CategoryDto createCategory(CategoryDto categoryDto);

    @Transactional
    CategoryDto updateCategory(Integer id, CategoryDto categoryDto);

    @Transactional
    void deleteCategory(Integer id);

    CategoryDto addTranslation(Integer categoryId, String languageCode, String translation);

    CategoryDto removeTranslation(Integer categoryId, String languageCode);

    List<CategoryDto> getAllCategoriesByQuery(String name, String lang);
}

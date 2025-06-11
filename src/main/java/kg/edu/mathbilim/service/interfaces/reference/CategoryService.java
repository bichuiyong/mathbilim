package kg.edu.mathbilim.service.interfaces.reference;


import kg.edu.mathbilim.dto.reference.CategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto getCategoryByName(String name);

    List<CategoryDto> getAllCategories();

    boolean existsByCategory(String email);

    CategoryDto createCategory(CategoryDto category);

    void deleteCategory(Integer category);

    CategoryDto updateCategory(CategoryDto category);

    CategoryDto getCategoryById(Integer category);
}

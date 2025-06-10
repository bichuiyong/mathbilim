package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.model.Category;
import kg.edu.mathbilim.model.reference.role.Role;

import java.util.List;

public interface CategoryService {
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
}

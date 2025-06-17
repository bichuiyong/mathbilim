package kg.edu.mathbilim.repository.reference;

import kg.edu.mathbilim.model.reference.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

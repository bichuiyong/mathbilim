package kg.edu.mathbilim.repository.reference.category;

import kg.edu.mathbilim.model.reference.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
}

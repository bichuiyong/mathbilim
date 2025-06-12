package kg.edu.mathbilim.repository.reference.category;

import kg.edu.mathbilim.model.reference.category.CategoryTranslation;
import kg.edu.mathbilim.model.reference.category.CategoryTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryTranslationRepository extends JpaRepository<CategoryTranslation, CategoryTranslationId> {
}

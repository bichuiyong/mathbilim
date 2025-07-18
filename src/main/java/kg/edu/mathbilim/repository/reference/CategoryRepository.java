package kg.edu.mathbilim.repository.reference;

import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CategoryRepository extends AbstractTypeRepository<Category> {

    @Query(value = "SELECT DISTINCT ON (c.id) c.*\n" +
            "FROM categories c\n" +
            "JOIN category_translations ct ON c.id = ct.type_id\n" +
            "WHERE (:lang IS NULL OR ct.language_code = :lang)\n" +
            "AND (:name IS NULL OR LOWER(ct.translation) LIKE CONCAT(LOWER(:name), '%'))\n" +
            "ORDER BY c.id, ct.type_id", nativeQuery = true)
    List<Category> findAllByQuery(@Param("name") String name, @Param("lang") String lang);
}

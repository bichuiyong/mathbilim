package kg.edu.mathbilim.repository.post;

import kg.edu.mathbilim.model.post.PostType;
import kg.edu.mathbilim.model.user.UserType;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostTypeRepository extends AbstractTypeRepository<PostType> {

    @Override
    @Query(value = "SELECT DISTINCT ON (p.id) p.*\n" +
            "FROM post_types p\n" +
            "JOIN post_type_translations pt ON p.id = pt.type_id\n" +
            "WHERE (:lang IS NULL OR pt.language_code = :lang)\n" +
            "AND (:name IS NULL OR LOWER(pt.translation) LIKE CONCAT(LOWER(:name), '%'))\n" +
            "ORDER BY p.id, pt.type_id", nativeQuery = true)
    List<PostType> findAllByQuery(@Param("name") String name, @Param("lang") String lang);
}

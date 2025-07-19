package kg.edu.mathbilim.repository.user;

import kg.edu.mathbilim.model.user.UserType;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserTypeRepository extends AbstractTypeRepository<UserType> {
    @Query(value = "SELECT DISTINCT ON (u.id) u.*\n" +
            "FROM user_types u\n" +
            "JOIN user_type_translations ut ON u.id = ut.type_id\n" +
            "WHERE (:lang IS NULL OR ut.language_code = :lang)\n" +
            "AND (:name IS NULL OR LOWER(ut.translation) LIKE CONCAT(LOWER(:name), '%'))\n" +
            "ORDER BY u.id, ut.type_id", nativeQuery = true)
    List<UserType> findAllByQuery(@Param("name") String name, @Param("lang") String lang);
}

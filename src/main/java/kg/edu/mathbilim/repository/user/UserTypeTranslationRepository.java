package kg.edu.mathbilim.repository.user;

import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import kg.edu.mathbilim.model.user.UserTypeTranslation;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeTranslationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTypeTranslationRepository extends AbstractTypeTranslationRepository<UserTypeTranslation> {


    default List<UserTypeTranslation> findByUserTypeId(Integer userTypeId) {
        return findByTypeId(userTypeId);
    }
    default Optional<UserTypeTranslation> findByUserTypeIdAndLanguageCode(Integer userTypeId,
                                                                          String languageCode) {
        return findByTypeIdAndLanguageCode(userTypeId, languageCode);
    }

    default void deleteByUserTypeId(Integer userTypeId) {
        deleteByTypeId(userTypeId);
    }

    default boolean existsByUserTypeIdAndLanguageCode(Integer userTypeId, String languageCode) {
        return existsByTypeIdAndLanguageCode(userTypeId, languageCode);
    }
}
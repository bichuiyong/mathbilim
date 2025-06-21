package kg.edu.mathbilim.repository.user;

import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import kg.edu.mathbilim.model.user.UserTypeTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTypeTranslationRepository extends JpaRepository<UserTypeTranslation, TypeTranslation.TranslationId> {

    @Query("SELECT utt FROM UserTypeTranslation utt WHERE utt.userType.id = :userTypeId")
    List<UserTypeTranslation> findByUserTypeId(@Param("userTypeId") Integer userTypeId);

    @Query("SELECT utt FROM UserTypeTranslation utt WHERE utt.languageCode = :languageCode")
    List<UserTypeTranslation> findByLanguageCode(@Param("languageCode") String languageCode);

    @Query("SELECT utt FROM UserTypeTranslation utt WHERE utt.userType.id = :userTypeId AND utt.languageCode = :languageCode")
    Optional<UserTypeTranslation> findByUserTypeIdAndLanguageCode(@Param("userTypeId") Integer userTypeId,
                                                                  @Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM UserTypeTranslation utt WHERE utt.userType.id = :userTypeId")
    void deleteByUserTypeId(@Param("userTypeId") Integer userTypeId);

    @Query("SELECT CASE WHEN COUNT(utt) > 0 THEN true ELSE false END FROM UserTypeTranslation utt " +
            "WHERE utt.userType.id = :userTypeId AND utt.languageCode = :languageCode")
    boolean existsByUserTypeIdAndLanguageCode(@Param("userTypeId") Integer userTypeId,
                                              @Param("languageCode") String languageCode);
}
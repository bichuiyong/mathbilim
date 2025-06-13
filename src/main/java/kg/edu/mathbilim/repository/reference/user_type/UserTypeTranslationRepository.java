package kg.edu.mathbilim.repository.reference.user_type;

import kg.edu.mathbilim.model.user.user_type.UserTypeTranslation;
import kg.edu.mathbilim.model.user.user_type.UserTypeTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTypeTranslationRepository extends JpaRepository<UserTypeTranslation, UserTypeTranslationId> {

    @Query("SELECT utt FROM UserTypeTranslation utt WHERE utt.id.userTypeId = :userTypeId")
    List<UserTypeTranslation> findByUserTypeId(@Param("userTypeId") Integer userTypeId);

    @Query("SELECT utt FROM UserTypeTranslation utt WHERE utt.id.languageCode = :languageCode")
    List<UserTypeTranslation> findByLanguageCode(@Param("languageCode") String languageCode);

    @Query("SELECT utt FROM UserTypeTranslation utt WHERE utt.id.userTypeId = :userTypeId AND utt.id.languageCode = :languageCode")
    Optional<UserTypeTranslation> findByUserTypeIdAndLanguageCode(@Param("userTypeId") Integer userTypeId,
                                                                  @Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM UserTypeTranslation utt WHERE utt.id.userTypeId = :userTypeId")
    void deleteByUserTypeId(@Param("userTypeId") Integer userTypeId);

    @Query("SELECT CASE WHEN COUNT(utt) > 0 THEN true ELSE false END FROM UserTypeTranslation utt " +
            "WHERE utt.id.userTypeId = :userTypeId AND utt.id.languageCode = :languageCode")
    boolean existsByUserTypeIdAndLanguageCode(@Param("userTypeId") Integer userTypeId,
                                              @Param("languageCode") String languageCode);
}
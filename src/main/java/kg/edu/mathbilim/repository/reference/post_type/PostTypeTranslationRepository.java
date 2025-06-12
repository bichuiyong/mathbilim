package kg.edu.mathbilim.repository.reference.post_type;

import kg.edu.mathbilim.model.reference.post_type.PostTypeTranslation;
import kg.edu.mathbilim.model.reference.post_type.PostTypeTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostTypeTranslationRepository extends JpaRepository<PostTypeTranslation, PostTypeTranslationId> {

    @Query("SELECT ptt FROM PostTypeTranslation ptt WHERE ptt.id.postTypeId = :postTypeId")
    List<PostTypeTranslation> findByPostTypeId(@Param("postTypeId") Integer postTypeId);

    @Query("SELECT ptt FROM PostTypeTranslation ptt WHERE ptt.id.languageCode = :languageCode")
    List<PostTypeTranslation> findByLanguageCode(@Param("languageCode") String languageCode);

    @Query("SELECT ptt FROM PostTypeTranslation ptt WHERE ptt.id.postTypeId = :postTypeId AND ptt.id.languageCode = :languageCode")
    Optional<PostTypeTranslation> findByPostTypeIdAndLanguageCode(@Param("postTypeId") Integer postTypeId,
                                                                  @Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM PostTypeTranslation ptt WHERE ptt.id.postTypeId = :postTypeId")
    void deleteByPostTypeId(@Param("postTypeId") Integer postTypeId);

    @Query("SELECT CASE WHEN COUNT(ptt) > 0 THEN true ELSE false END FROM PostTypeTranslation ptt " +
            "WHERE ptt.id.postTypeId = :postTypeId AND ptt.id.languageCode = :languageCode")
    boolean existsByPostTypeIdAndLanguageCode(@Param("postTypeId") Integer postTypeId,
                                              @Param("languageCode") String languageCode);
}
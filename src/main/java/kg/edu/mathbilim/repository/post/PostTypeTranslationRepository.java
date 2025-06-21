package kg.edu.mathbilim.repository.post;

import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import kg.edu.mathbilim.model.post.PostTypeTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostTypeTranslationRepository extends JpaRepository<PostTypeTranslation, TypeTranslation.TranslationId> {

    @Query("SELECT ptt FROM PostTypeTranslation ptt WHERE ptt.postType.id = :postTypeId")
    List<PostTypeTranslation> findByPostTypeId(@Param("postTypeId") Integer postTypeId);

    @Query("SELECT ptt FROM PostTypeTranslation ptt WHERE ptt.translationId.languageCode = :languageCode")
    List<PostTypeTranslation> findByLanguageCode(@Param("languageCode") String languageCode);

    @Query("SELECT ptt FROM PostTypeTranslation ptt WHERE ptt.postType.id = :postTypeId AND ptt.translationId.languageCode = :languageCode")
    Optional<PostTypeTranslation> findByPostTypeIdAndLanguageCode(@Param("postTypeId") Integer postTypeId,
                                                                  @Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM PostTypeTranslation ptt WHERE ptt.postType.id = :postTypeId")
    void deleteByPostTypeId(@Param("postTypeId") Integer postTypeId);

    @Query("SELECT CASE WHEN COUNT(ptt) > 0 THEN true ELSE false END FROM PostTypeTranslation ptt " +
            "WHERE ptt.postType.id = :postTypeId AND ptt.translationId.languageCode = :languageCode")
    boolean existsByPostTypeIdAndLanguageCode(@Param("postTypeId") Integer postTypeId,
                                              @Param("languageCode") String languageCode);
}
package kg.edu.mathbilim.repository.reference;

import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import kg.edu.mathbilim.model.reference.category.CategoryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryTranslationRepository extends JpaRepository<CategoryTranslation, TypeTranslation.TranslationId> {

    @Query("SELECT ct FROM CategoryTranslation ct WHERE ct.category.id = :categoryId")
    List<CategoryTranslation> findByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("SELECT ct FROM CategoryTranslation ct WHERE ct.languageCode = :languageCode")
    List<CategoryTranslation> findByLanguageCode(@Param("languageCode") String languageCode);

    @Query("SELECT ct FROM CategoryTranslation ct WHERE ct.category.id = :categoryId AND ct.languageCode = :languageCode")
    Optional<CategoryTranslation> findByCategoryIdAndLanguageCode(@Param("categoryId") Integer categoryId,
                                                                  @Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM CategoryTranslation ct WHERE ct.category.id = :categoryId")
    void deleteByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("SELECT CASE WHEN COUNT(ct) > 0 THEN true ELSE false END FROM CategoryTranslation ct " +
            "WHERE ct.category.id = :categoryId AND ct.languageCode = :languageCode")
    boolean existsByCategoryIdAndLanguageCode(@Param("categoryId") Integer categoryId,
                                              @Param("languageCode") String languageCode);
}
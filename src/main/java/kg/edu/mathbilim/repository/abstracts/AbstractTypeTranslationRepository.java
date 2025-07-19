package kg.edu.mathbilim.repository.abstracts;

import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import kg.edu.mathbilim.model.post.PostTypeTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
@NoRepositoryBean
public interface AbstractTypeTranslationRepository<T extends TypeTranslation>
        extends JpaRepository<T, TypeTranslation.TranslationId> {
    @Query("SELECT t FROM #{#entityName} t WHERE t.id.typeId = :typeId")
    List<T> findByTypeId(@Param("typeId") Integer typeId);

    @Query("SELECT t FROM #{#entityName} t WHERE t.id.languageCode = :languageCode")
    List<T> findByLanguageCode(@Param("languageCode") String languageCode);

    @Query("SELECT t FROM #{#entityName} t WHERE t.id.typeId = :typeId AND t.id.languageCode = :languageCode")
    Optional<T> findByTypeIdAndLanguageCode(@Param("typeId") Integer typeId,
                                            @Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM #{#entityName} t WHERE t.id.typeId = :typeId")
    void deleteByTypeId(@Param("typeId") Integer typeId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM #{#entityName} t " +
            "WHERE t.id.typeId = :typeId AND t.id.languageCode = :languageCode")
    boolean existsByTypeIdAndLanguageCode(@Param("typeId") Integer typeId,
                                          @Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM #{#entityName} t WHERE t.id.typeId = :typeId and t.id.languageCode = :languageCode")
    void deleteByTypeIdAndLanguageCode(Integer typeId, String languageCode);
}

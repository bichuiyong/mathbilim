package kg.edu.mathbilim.repository.post;

import kg.edu.mathbilim.model.post.PostTranslation;
import kg.edu.mathbilim.model.post.PostTranslationId;
import kg.edu.mathbilim.repository.abstracts.BaseTranslationRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTranslationRepository extends BaseTranslationRepository<PostTranslation, PostTranslationId> {

    @Query("SELECT pt FROM PostTranslation pt WHERE pt.id.postId = :postId")
    List<PostTranslation> findByPostId(@Param("postId") Long postId);

    @Query("SELECT pt FROM PostTranslation pt WHERE pt.id.languageCode = :languageCode")
    List<PostTranslation> findByIdLanguageCode(@Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM PostTranslation pt WHERE pt.id.postId = :postId")
    void deleteByPostId(@Param("postId") Long postId);

    @Query("""
            SELECT COUNT(t) > 0 FROM PostTranslation t
            WHERE t.post.id = :postId AND t.id.languageCode = :languageCode
            """)
    boolean existsTranslation(@Param("postId") Long postId, @Param("languageCode") String languageCode);
}
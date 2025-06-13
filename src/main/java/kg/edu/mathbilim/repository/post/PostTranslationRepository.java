package kg.edu.mathbilim.repository.post;

import kg.edu.mathbilim.model.post.PostTranslation;
import kg.edu.mathbilim.model.post.PostTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTranslationRepository extends JpaRepository<PostTranslation, PostTranslationId> {

    @Query("SELECT pt FROM PostTranslation pt WHERE pt.id.postId = :postId")
    List<PostTranslation> findByPostId(@Param("postId") Long postId);

    @Query("SELECT pt FROM PostTranslation pt WHERE pt.id.languageCode = :languageCode")
    List<PostTranslation> findByIdLanguageCode(@Param("languageCode") String languageCode);

    @Modifying
    @Query("DELETE FROM PostTranslation pt WHERE pt.id.postId = :postId")
    void deleteByPostId(@Param("postId") Long postId);
}
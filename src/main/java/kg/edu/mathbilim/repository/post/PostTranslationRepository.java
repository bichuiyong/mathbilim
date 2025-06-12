package kg.edu.mathbilim.repository.post;

import kg.edu.mathbilim.model.post.PostTranslation;
import kg.edu.mathbilim.model.post.PostTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTranslationRepository extends JpaRepository<PostTranslation, PostTranslationId> {
}

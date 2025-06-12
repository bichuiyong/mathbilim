
package kg.edu.mathbilim.repository.reference.post_type;

import kg.edu.mathbilim.model.reference.post_type.PostTypeTranslation;
import kg.edu.mathbilim.model.reference.post_type.PostTypeTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTypeTranslationRepository extends JpaRepository<PostTypeTranslation, PostTypeTranslationId> {
}

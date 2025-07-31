package kg.edu.mathbilim.repository.post;

import kg.edu.mathbilim.model.post.PostTypeTranslation;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeTranslationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostTypeTranslationRepository extends AbstractTypeTranslationRepository<PostTypeTranslation> {

}
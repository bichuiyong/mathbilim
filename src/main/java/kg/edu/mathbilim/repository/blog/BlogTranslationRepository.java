package kg.edu.mathbilim.repository.blog;

import kg.edu.mathbilim.model.blog.BlogTranslation;
import kg.edu.mathbilim.model.blog.BlogTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogTranslationRepository extends JpaRepository<BlogTranslation, BlogTranslationId> {
}

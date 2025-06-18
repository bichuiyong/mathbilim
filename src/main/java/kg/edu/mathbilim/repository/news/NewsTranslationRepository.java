package kg.edu.mathbilim.repository.news;

import kg.edu.mathbilim.model.news.NewsTranslation;
import kg.edu.mathbilim.model.news.NewsTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTranslationRepository extends JpaRepository<NewsTranslation, NewsTranslationId> {
}

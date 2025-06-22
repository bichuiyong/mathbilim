package kg.edu.mathbilim.repository.news;

import kg.edu.mathbilim.model.news.NewsTranslation;
import kg.edu.mathbilim.model.news.NewsTranslationId;
import kg.edu.mathbilim.repository.abstracts.BaseTranslationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsTranslationRepository extends BaseTranslationRepository<NewsTranslation, NewsTranslationId> {

    void deleteByNews_Id(Long entityId);
}

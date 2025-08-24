package kg.edu.mathbilim.repository.news;

import kg.edu.mathbilim.model.news.NewsTranslation;
import kg.edu.mathbilim.model.news.NewsTranslationId;
import kg.edu.mathbilim.repository.abstracts.BaseTranslationRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface NewsTranslationRepository extends BaseTranslationRepository<NewsTranslation, NewsTranslationId> {

    @Modifying
    @Transactional
    @Query("UPDATE NewsTranslation pt SET pt.deleted = true WHERE pt.id.newsId = :entityId")
    void deleteByNews_Id(Long entityId);
}

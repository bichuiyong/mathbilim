package kg.edu.mathbilim.repository.news;

import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.repository.abstracts.BaseContentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long>, BaseContentRepository<News> {
    Optional<News> findByIdAndCreatorId(long id, long userId);
}

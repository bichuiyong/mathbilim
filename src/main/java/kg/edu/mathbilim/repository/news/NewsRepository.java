package kg.edu.mathbilim.repository.news;

import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.repository.abstracts.BaseContentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long>, BaseContentRepository<News> {
    Optional<News> findByIdAndCreatorId(long id, long userId);

    @Modifying
    @Query("UPDATE News b SET b.viewCount = b.viewCount + 1 WHERE b.id = :blogId")
    void incrementViewCount(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE News b SET b.shareCount = b.shareCount + 1 WHERE b.id = :blogId")
    void incrementShareCount(@Param("blogId") Long blogId);
}

package kg.edu.mathbilim.repository.news;

import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.repository.abstracts.BaseContentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long>, BaseContentRepository<News> {
    Optional<News> findByIdAndCreatorId(long id, long userId);

    @Modifying
    @Query("UPDATE News b SET b.viewCount = b.viewCount + 1 WHERE b.id = :blogId")
    void incrementViewCount(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE News b SET b.shareCount = b.shareCount + 1 WHERE b.id = :blogId")
    void incrementShareCount(@Param("blogId") Long blogId);


    @Query(value = "SELECT DISTINCT n FROM News n " +
            "JOIN n.newsTranslations t " +
            "where t.id.languageCode = :lang")
    Page<News> findByNewsWithLang(@Param("lang") String lang, Pageable pageable);


    @Query("""
            SELECT DISTINCT n FROM News n
                                    JOIN n.newsTranslations t
                                    where t.id.languageCode = :lang
                                    and LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
            """)

    Page<News> findNewsByQuery(@Param("query") String query, @Param("lang") String lang, Pageable pageable);


    @Query("SELECT n FROM News n")
    Page<News> findAllNews(Pageable pageable);


}

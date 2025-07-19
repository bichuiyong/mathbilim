package kg.edu.mathbilim.repository.post;

import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.repository.abstracts.BaseContentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, BaseContentRepository<Post> {

    Page<Post> getPostByCreator_Id(Long userId, Pageable pageable);

    @Modifying
    @Query("UPDATE Post b SET b.viewCount = b.viewCount + 1 WHERE b.id = :blogId")
    void incrementViewCount(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE Post b SET b.shareCount = b.shareCount + 1 WHERE b.id = :blogId")
    void incrementShareCount(@Param("blogId") Long blogId);

    @Query("""
            SELECT DISTINCT p FROM Post p
            JOIN p.postTranslations t
            WHERE p.creator.id = :userId
                        AND
            LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
            ORDER BY p.createdAt DESC
            """)
    Page<Post> getUserPostsWithQuery(@Param("userId") Long userId,
                                     @Param("query") String query,
                                     Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Post p
            JOIN p.postTranslations t
            WHERE p.status = :contentStatus
            ORDER BY p.createdAt DESC
            """)
    Page<Post> getPostsByStatus(ContentStatus contentStatus, Pageable pageable);


    @Query("""
    SELECT DISTINCT p FROM Post p
    JOIN p.postTranslations t
    WHERE p.status = :contentStatus
      AND t.id.languageCode = :languageCode
    """)
    Page<Post> getPostsByStatusWithLang(@Param("contentStatus") ContentStatus contentStatus,
                                        @Param("languageCode") String languageCode,
                                        Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Post p
            JOIN p.postTranslations t
            WHERE p.status = :contentStatus
                        AND
            LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) and
                        t.id.languageCode = :languageCode
            ORDER BY p.createdAt DESC
            """)
    Page<Post> getPostsByStatusWithQuery(ContentStatus contentStatus,
                                         String query,
                                         Pageable pageable, String languageCode);

    @Override
    @Query("""
            SELECT DISTINCT p FROM Post p
            LEFT JOIN p.postTranslations t
            WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    Page<Post> findByQuery(@Param("query") String query, Pageable pageable);


    @Query("""
            SELECT DISTINCT p FROM Post p
            JOIN p.postTranslations t
            WHERE t.id.languageCode = :languageCode
              AND (LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')))
            ORDER BY p.createdAt DESC
            """)
    Page<Post> findByTranslationQueryAndLanguage(@Param("query") String query,
                                                 @Param("languageCode") String languageCode,
                                                 Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Post p
            JOIN p.postTranslations t
            WHERE p.status = :status
              AND (LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                   OR LOWER(t.content) LIKE LOWER(CONCAT('%', :query, '%')))
            ORDER BY p.createdAt DESC
            """)
    Page<Post> findByTranslationQueryAndStatus(@Param("query") String query,
                                               @Param("status") ContentStatus status,
                                               Pageable pageable);


    @Query("""
            SELECT DISTINCT p FROM Post p
            JOIN p.postTranslations t
            WHERE p.status IN :statuses
              AND (LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                   OR LOWER(t.content) LIKE LOWER(CONCAT('%', :query, '%')))
            ORDER BY p.createdAt DESC
            """)
    Page<Post> findByTranslationQueryAndStatuses(@Param("query") String query,
                                                 @Param("statuses") List<ContentStatus> statuses,
                                                 Pageable pageable);
}
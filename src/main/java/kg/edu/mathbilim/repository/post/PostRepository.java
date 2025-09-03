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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, BaseContentRepository<Post> {

    Page<Post> getPostByCreator_Id(Long userId, Pageable pageable);

    @Modifying
    @Query("UPDATE Post b SET b.viewCount = b.viewCount + 1 WHERE b.id = :blogId  and b.deleted=false")
    void incrementViewCount(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE Post b SET b.shareCount = b.shareCount + 1 WHERE b.id = :blogId  and b.deleted=false")
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
            WHERE
            LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                        and p.deleted = false
            ORDER BY p.createdAt DESC
            """)
    Page<Post> getPostByQuery( @Param("query") String query,
                                     Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Post p
               WHERE p.status = :contentStatus
                    and p.deleted=false
            """)
    Page<Post> getPostsByStatus(ContentStatus contentStatus, Pageable pageable);

    List<Post> findTop10ByStatusOrderByCreatedAtDesc(ContentStatus status);

    @Query("""
            SELECT p FROM Post p
           WHERE p.id IN (
               SELECT t.post.id FROM PostTranslation t
               WHERE t.id.languageCode = :languageCode
                 AND LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
           )
           AND p.status = :contentStatus
           AND p.deleted = false
            """)
    Page<Post> getPostsByStatusWithQuery(ContentStatus contentStatus,
                                         String query,
                                         Pageable pageable, String languageCode);


    @Query("""
                SELECT DISTINCT p FROM Post p
                JOIN p.postTranslations t
                WHERE p.status = :status
                  AND LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                  AND p.creator.id = :userId
                ORDER BY p.createdAt DESC
            """)
    Page<Post> getPostsByStatusAndQuery(
            @Param("status") ContentStatus status,
            @Param("query") String query,
            @Param("userId") Long userId,
            Pageable pageable
    );


    @Query("""
            SELECT DISTINCT p FROM Post p
            JOIN p.postTranslations t
            WHERE p.status = :contentStatus
                        AND
            p.creator.id = :userId
                 and p.deleted=false
            """)
    Page<Post> findPostByStatus(ContentStatus contentStatus,
                                Long userId,
                                Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Post p
            JOIN p.postTranslations t
            WHERE p.status = :contentStatus
                        AND
            LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                 and p.deleted=false
                              and
                        p.deleted=false
            ORDER BY p.createdAt DESC
            """)
    Page<Post> getPostsByQuery(ContentStatus contentStatus,
                               String query,
                               Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Post p
            WHERE p.status = :contentStatus
                        AND
            p.creator.id = :userId
                 and p.deleted=false
            ORDER BY p.createdAt DESC
            """)
    Page<Post> getPostsByCreatorId(ContentStatus contentStatus,
                                   Long userId,
                                   Pageable pageable);


    @Query("""
            SELECT DISTINCT p FROM Post p
            JOIN p.postTranslations t
            WHERE p.status = :contentStatus
                        AND
            LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                 and p.deleted=false
            ORDER BY p.createdAt DESC
            """)
    Page<Post> getPostsByStatus(ContentStatus contentStatus,
                                String query,
                                Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Blog p
            JOIN p.blogTranslations t
            WHERE p.status = :contentStatus
                 and p.deleted=false
            ORDER BY p.createdAt DESC
            """)
    Page<Post> findPostsByStatus(ContentStatus status, Pageable pageable);

    @Override
    @Query("""
            SELECT DISTINCT p FROM Post p
            LEFT JOIN p.postTranslations t
            WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                 and p.deleted=false
            """)
    Page<Post> findByQuery(@Param("query") String query, Pageable pageable);

    Page<Post> findAllByDeletedFalse(Pageable pageable);



    @Query("""
            SELECT DISTINCT p FROM Post p
            JOIN p.postTranslations t
            WHERE t.id.languageCode = :languageCode
              AND (LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')))
                   and p.deleted=false
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
                        and p.deleted=false
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
                        and p.deleted=false
            ORDER BY p.createdAt DESC
            """)
    Page<Post> findByTranslationQueryAndStatuses(@Param("query") String query,
                                                 @Param("statuses") List<ContentStatus> statuses,
                                                 Pageable pageable);


    Long countByStatus(ContentStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.deleted = true WHERE p.id = :postId")
    void deleteContentById(@Param("postId") Long postId);


}
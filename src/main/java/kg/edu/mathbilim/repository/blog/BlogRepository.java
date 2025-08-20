package kg.edu.mathbilim.repository.blog;

import kg.edu.mathbilim.dto.abstracts.DisplayContentDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.blog.Blog;
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
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, BaseContentRepository<Blog> {

    @Query("""
                SELECT b FROM Blog b 
                JOIN b.blogTranslations bt 
                WHERE b.id = :blogId 
                AND bt.id.languageCode = :languageCode
            """)
    Optional<Blog> findDisplayBlogById(@Param("blogId") Long blogId,
                                                    @Param("languageCode") String languageCode);

    List<Blog> findTop10ByStatusOrderByCreatedAtDesc(ContentStatus status);

    @Query("""
                SELECT b FROM Blog b
                JOIN b.blogTranslations bt
                WHERE b.id = :blogId
            """)
    Optional<Blog> findDisplayBlogById(@Param("blogId") Long blogId);

    @Query("""
                SELECT new kg.edu.mathbilim.dto.abstracts.DisplayContentDto(
                    b.id,
                    b.creator.id,
                    b.createdAt, 
                    b.updatedAt, 
                    b.viewCount, 
                    b.shareCount,
                    b.mainImage.id,
                    b.approvedBy.id,
                    b.status,
                    bt.title, 
                    bt.content
                )
                FROM Blog b 
                JOIN b.blogTranslations bt 
                WHERE bt.id.languageCode = :languageCode
                  AND bt.title IS NOT NULL 
                  AND bt.title != ''
                  AND b.status = kg.edu.mathbilim.enums.ContentStatus.APPROVED
                ORDER BY b.createdAt DESC
            """)
    Page<DisplayContentDto> findAllDisplayBlogsByLanguage(@Param("languageCode") String languageCode,
                                                          Pageable pageable);


    @Query("""
                SELECT b
                FROM Blog b 
                JOIN b.blogTranslations bt 
                WHERE bt.id.languageCode = :languageCode
                  AND bt.title IS NOT NULL 
                  AND bt.title != ''
                  AND b.status = kg.edu.mathbilim.enums.ContentStatus.APPROVED
                ORDER BY b.createdAt DESC
            """)
    Page<Blog> findAllDisplayBlogsByLanguageBlog(@Param("languageCode") String languageCode,
                                             Pageable pageable);

    @Query("""
                SELECT new kg.edu.mathbilim.dto.abstracts.DisplayContentDto(
                    b.id,
                    b.creator.id,
                    b.createdAt, 
                    b.updatedAt, 
                    b.viewCount, 
                    b.shareCount,
                    b.mainImage.id,
                    b.approvedBy.id,
                    b.status,
                    bt.title, 
                    bt.content
                )
                FROM Blog b 
                JOIN b.blogTranslations bt 
                WHERE b.id != :excludeId
                AND bt.id.languageCode = :languageCode
                AND bt.title IS NOT NULL 
                AND bt.title != ''
                ORDER BY b.viewCount DESC, b.createdAt DESC
            """)
    List<DisplayContentDto> findRelatedBlogs(@Param("excludeId") Long excludeId,
                                             @Param("languageCode") String languageCode,
                                             Pageable pageable);

    @Modifying
    @Query("UPDATE Blog b SET b.viewCount = b.viewCount + 1 WHERE b.id = :blogId")
    void incrementViewCount(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE Blog b SET b.shareCount = b.shareCount + 1 WHERE b.id = :blogId")
    void incrementShareCount(@Param("blogId") Long blogId);

    @Query("""
            SELECT DISTINCT p FROM Blog p
            JOIN p.blogTranslations t
            WHERE p.status = :contentStatus
            ORDER BY p.createdAt DESC
            """)
    Page<Blog> findBlogsByStatus(ContentStatus status, Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Blog p
            JOIN p.blogTranslations t
            WHERE p.status = :contentStatus
                        AND
            LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
            ORDER BY p.createdAt DESC
            """)
    Page<Blog> getBlogsByStatusWithQuery(ContentStatus contentStatus,
                                         String query,
                                         Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Blog p
            JOIN p.blogTranslations t
            WHERE p.status = :contentStatus
                        AND
            LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) and
                        t.id.languageCode = :languageCode
            ORDER BY p.createdAt DESC
            """)
    Page<Blog> getBlogsByStatusWithQueryAndLang(ContentStatus contentStatus,
                                         String query,
                                         Pageable pageable, String languageCode);


    @Query("""
                SELECT DISTINCT p FROM Blog p
                JOIN p.blogTranslations t
                WHERE p.status = :contentStatus
                  AND p.creator.id = :userId
                  AND LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                ORDER BY p.createdAt DESC
            """)
    Page<Blog> getBlogsByCreatorAndStatusAndQuery(@Param("contentStatus") ContentStatus contentStatus,
                                                  @Param("userId") Long userId,
                                                  @Param("query") String query,
                                                  Pageable pageable);


    @Query("""
                SELECT DISTINCT p FROM Blog p
                JOIN p.blogTranslations t
                WHERE p.creator.id = :userId
                  AND LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                ORDER BY p.createdAt DESC
            """)
    Page<Blog> getBlogsWithQuery(@Param("query") String query,
                                 @Param("userId") Long userId,
                                 Pageable pageable);


    @Query("""
            SELECT DISTINCT b FROM Blog b
               WHERE b.status = :contentStatus
            """)
    Page<Blog> getBlogsByStatus(ContentStatus contentStatus, Pageable pageable);


    @Query("""
                SELECT DISTINCT b FROM Blog b
                WHERE b.status = :contentStatus
                  AND b.creator.id = :userId
            """)
    Page<Blog> getBlogsByCreatorAndStatus(@Param("contentStatus") ContentStatus contentStatus,
                                          @Param("userId") Long userId,
                                          Pageable pageable);

    Long countByStatus(ContentStatus contentStatus);
}
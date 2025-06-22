package kg.edu.mathbilim.repository.blog;

import kg.edu.mathbilim.dto.blog.DisplayBlogDto;
import kg.edu.mathbilim.model.blog.Blog;
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
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("""
        SELECT new kg.edu.mathbilim.dto.blog.DisplayBlogDto(
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
        WHERE b.id = :blogId 
        AND bt.id.languageCode = :languageCode
    """)
    Optional<DisplayBlogDto> findDisplayBlogById(@Param("blogId") Long blogId,
                                                 @Param("languageCode") String languageCode);

    @Query("""
        SELECT new kg.edu.mathbilim.dto.blog.DisplayBlogDto(
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
        ORDER BY b.createdAt DESC
    """)
    Page<DisplayBlogDto> findAllDisplayBlogsByLanguage(@Param("languageCode") String languageCode,
                                                       Pageable pageable);

    @Query("""
        SELECT new kg.edu.mathbilim.dto.blog.DisplayBlogDto(
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
    List<DisplayBlogDto> findRelatedBlogs(@Param("excludeId") Long excludeId,
                                          @Param("languageCode") String languageCode,
                                          Pageable pageable);

    @Modifying
    @Query("UPDATE Blog b SET b.viewCount = b.viewCount + 1 WHERE b.id = :blogId")
    void incrementViewCount(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE Blog b SET b.shareCount = b.shareCount + 1 WHERE b.id = :blogId")
    void incrementShareCount(@Param("blogId") Long blogId);
}
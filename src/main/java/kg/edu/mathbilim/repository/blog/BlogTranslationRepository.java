package kg.edu.mathbilim.repository.blog;

import kg.edu.mathbilim.model.blog.BlogTranslation;
import kg.edu.mathbilim.model.blog.BlogTranslationId;
import kg.edu.mathbilim.repository.abstracts.BaseTranslationRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BlogTranslationRepository extends BaseTranslationRepository<BlogTranslation, BlogTranslationId> {

    @Modifying
    @Query("DELETE FROM BlogTranslation bt WHERE bt.id.blogId = :blogId")
    void deleteByBlogId(@Param("blogId") Long blogId);
}
package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT c.* FROM comments c " +
            "JOIN post_comments pc ON c.id = pc.comment_id " +
            "WHERE pc.post_id = :postId", nativeQuery = true)
    List<Comment> findByPostsId(Long postId);

    @Query(value = "SELECT c.* FROM comments c " +
            "JOIN blog_comments bc ON c.id = bc.comment_id " +
            "WHERE bc.blog_id = :blogId", nativeQuery = true)
    List<Comment> findByBlogsId(@Param("blogId") Long blogId);

    @Query(value = "SELECT c.* FROM comments c " +
            "JOIN news_comments nc ON c.id = nc.comment_id " +
            "WHERE nc.news_id = :newsId", nativeQuery = true)
    List<Comment> findByNewsId(@Param("newsId") Long newsId);

};

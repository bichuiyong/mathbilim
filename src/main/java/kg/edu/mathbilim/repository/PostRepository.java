package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.enums.PostType;
import kg.edu.mathbilim.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
    SELECT DISTINCT p FROM Post p
    WHERE p.type IN :type
      AND (
            LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) 
         OR LOWER(p.slug) LIKE LOWER(CONCAT('%', :query, '%'))
      )
""")
    Page<Post> findByQuery(PostType type,String query, Pageable pageable);

    Page<Post> findAllByType(PostType type, Pageable pageable);
}

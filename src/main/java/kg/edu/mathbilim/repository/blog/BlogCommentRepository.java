package kg.edu.mathbilim.repository.blog;

import kg.edu.mathbilim.model.blog.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
}

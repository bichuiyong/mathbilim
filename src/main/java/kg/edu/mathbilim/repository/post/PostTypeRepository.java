package kg.edu.mathbilim.repository.post;

import kg.edu.mathbilim.model.post.post_type.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTypeRepository extends JpaRepository<PostType, Integer> {
}

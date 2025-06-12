package kg.edu.mathbilim.repository.reference.post_type;

import kg.edu.mathbilim.model.reference.post_type.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostTypeRepository extends JpaRepository<PostType, Integer> {
    Optional<PostType> findByName(String name);
}

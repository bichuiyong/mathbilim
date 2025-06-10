package kg.edu.mathbilim.repository.reference;

import kg.edu.mathbilim.model.reference.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostTypeRepository extends JpaRepository<PostType, Integer> {
    Optional<PostType> findByName(String name);
}

package kg.edu.mathbilim.repository.reference.types;

import kg.edu.mathbilim.model.reference.types.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType, Integer> {
}

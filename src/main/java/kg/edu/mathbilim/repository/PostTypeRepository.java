package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType, Integer> {
}

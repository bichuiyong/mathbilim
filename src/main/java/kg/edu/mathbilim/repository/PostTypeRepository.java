package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.PostType;
import kg.edu.mathbilim.model.reference.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostTypeRepository extends JpaRepository<PostType, Integer> {
    Optional<PostType> findByName(String name);
}

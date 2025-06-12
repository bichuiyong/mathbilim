package kg.edu.mathbilim.repository.reference.user_type;

import kg.edu.mathbilim.model.reference.user_type.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    Optional<UserType> findByName(String name);

    boolean existsByName(String name);
}

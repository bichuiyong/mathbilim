package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.reference.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    Optional<UserType> findByName(String name);

    boolean existsByName(String name);
}

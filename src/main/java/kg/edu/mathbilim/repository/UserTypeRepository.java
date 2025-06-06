package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    Optional<UserType> findByName(String typeName);

    List<UserType> findAll();

    Optional<UserType> findById(Integer id);
}

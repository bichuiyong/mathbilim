package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

//    Boolean existsByEmail(String email);
//
//    Optional<User> findByResetPasswordToken(String resetPasswordToken);
}

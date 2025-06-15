package kg.edu.mathbilim.repository.user;

import kg.edu.mathbilim.model.user.user_type.UserType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
}

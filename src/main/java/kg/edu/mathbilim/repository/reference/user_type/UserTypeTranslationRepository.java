
package kg.edu.mathbilim.repository.reference.user_type;

import kg.edu.mathbilim.model.reference.user_type.UserTypeTranslation;
import kg.edu.mathbilim.model.reference.user_type.UserTypeTranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeTranslationRepository extends JpaRepository<UserTypeTranslation, UserTypeTranslationId> {
}

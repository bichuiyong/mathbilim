package kg.edu.mathbilim.repository.notification;

import kg.edu.mathbilim.model.notifications.NotificationType;
import kg.edu.mathbilim.model.notifications.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<UserNotification, Long> {
    boolean existsByUserIdAndTypeId(Long userId, Integer type);
    List<UserNotification> findByType(NotificationType type);
}

package kg.edu.mathbilim.repository.notification;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;
import kg.edu.mathbilim.model.notifications.UserNotification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.management.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<UserNotification, Long> {
    @Query("SELECT un FROM UserNotification un WHERE un.type = :type")
    List<UserNotification> findByType(@Param("type") NotificationType type);

    boolean existsByUserIdAndTypeId(Long userId, Integer typeId);

    @Query("SELECT un FROM UserNotification un JOIN FETCH un.user u JOIN FETCH u.role WHERE un.type = :type")
    List<UserNotification> findByTypeWithUser(@Param("type") NotificationType type);

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT un  FROM UserNotification un WHERE un.type.id = :typeId")
    List<UserNotification> findByTypeWithUserNative(@Param("typeId") Integer typeId);

    Optional<UserNotification> findByUserIdAndTypeId(Long userId, Integer typeId);
}

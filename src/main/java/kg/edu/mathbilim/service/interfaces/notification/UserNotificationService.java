package kg.edu.mathbilim.service.interfaces.notification;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import org.springframework.transaction.annotation.Transactional;

public interface UserNotificationService {
    void subscribe(String email, NotificationEnum notificationType);

    @Transactional(readOnly = true)
    void notifyAllSubscribed(NotificationEnum type, String message) ;
}

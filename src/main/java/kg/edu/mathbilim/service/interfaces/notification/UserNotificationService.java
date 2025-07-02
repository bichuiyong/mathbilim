package kg.edu.mathbilim.service.interfaces.notification;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.UserNotification;
import org.springframework.transaction.annotation.Transactional;

public interface UserNotificationService {
    void subscribe( NotificationEnum notificationType);

    @Transactional(readOnly = true)
    void notifyAllSubscribed(NotificationEnum type, String message) ;

    void unsubscribe ( NotificationEnum notificationType);

    boolean isSubscribed(NotificationEnum notificationType);
}

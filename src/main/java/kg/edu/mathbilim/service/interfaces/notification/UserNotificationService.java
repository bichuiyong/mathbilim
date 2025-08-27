package kg.edu.mathbilim.service.interfaces.notification;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.UserNotification;
import kg.edu.mathbilim.telegram.service.NotificationData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserNotificationService {


    void subscribe(NotificationEnum notificationType);

    boolean subscribe(NotificationEnum notificationType, Long chatId);

    void unsubscribe(NotificationEnum notificationType);

    boolean unsubscribe(NotificationEnum notificationType, Long chatId);

    boolean isSubscribed(NotificationEnum notificationType);

    List<String> getAllSubscribedUsersType(Long chatId);

    int subscribeToAllT(Long chatId);

    void unsubscribeFromAllT(Long chatId);
}

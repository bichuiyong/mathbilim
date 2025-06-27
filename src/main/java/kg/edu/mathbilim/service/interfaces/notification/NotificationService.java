package kg.edu.mathbilim.service.interfaces.notification;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;
import org.telegram.telegrambots.meta.generics.TelegramBot;

public interface NotificationService {
    void subscribe(String email, NotificationEnum notificationType);

    void notifyAllSubscribed(NotificationType type, String message, TelegramBot bot);
}

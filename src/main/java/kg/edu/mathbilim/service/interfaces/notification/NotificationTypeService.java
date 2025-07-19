package kg.edu.mathbilim.service.interfaces.notification;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;

public interface NotificationTypeService {
    NotificationType findByName(NotificationEnum name);
}

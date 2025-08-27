package kg.edu.mathbilim.telegram.service;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.telegram.enumTelegram.SubscriptionResult;
import kg.edu.mathbilim.telegram.enumTelegram.UnsubscriptionResult;

import java.util.List;

public interface TelegramUserService {
    List<String> getAllSubscribedUsersType(Long chatId);

    SubscriptionResult subscribe(NotificationEnum notificationType, Long chatId);

    UnsubscriptionResult unsubscribe(NotificationEnum notificationType, Long chatId);

    int subscribeToAll(Long chatId);

    void unsubscribeFromAll(Long chatId);
}

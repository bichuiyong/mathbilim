package kg.edu.mathbilim.telegram.service;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import kg.edu.mathbilim.telegram.enumTelegram.SubscriptionResult;
import kg.edu.mathbilim.telegram.enumTelegram.UnsubscriptionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramUserServiceImpl implements TelegramUserService {

    private final UserNotificationService userNotificationService;

    @Override
    public List<String> getAllSubscribedUsersType(Long chatId) {
        return userNotificationService.getAllSubscribedUsersType(chatId);
    }

    @Override
    public SubscriptionResult subscribe(NotificationEnum notificationType, Long chatId) {
        log.info("Попытка подписки: type={}, chatId={}", notificationType, chatId);

        try {
            boolean result = userNotificationService.subscribe(notificationType, chatId);

            if (result) {
                log.info("Подписка успешно создана: type={}, chatId={}", notificationType, chatId);
                return SubscriptionResult.SUCCESS;
            } else {
                log.info("Пользователь уже подписан: type={}, chatId={}", notificationType, chatId);
                return SubscriptionResult.ALREADY_SUBSCRIBED;
            }
        } catch (Exception e) {
            log.error("Ошибка при подписке: type={}, chatId={}", notificationType, chatId, e);
            return SubscriptionResult.ERROR;
        }
    }

    @Override
    public UnsubscriptionResult unsubscribe(NotificationEnum notificationType, Long chatId) {
        log.info("Попытка отписки: type={}, chatId={}", notificationType, chatId);

        try {
            boolean result = userNotificationService.unsubscribe(notificationType, chatId);

            if (result) {
                log.info("Отписка успешно выполнена: type={}, chatId={}", notificationType, chatId);
                return UnsubscriptionResult.SUCCESS;
            } else {
                log.info("Пользователь не был подписан: type={}, chatId={}", notificationType, chatId);
                return UnsubscriptionResult.NOT_SUBSCRIBED;
            }
        } catch (Exception e) {
            log.error("Ошибка при отписке: type={}, chatId={}", notificationType, chatId, e);
            return UnsubscriptionResult.ERROR;
        }
    }

    @Override
    public int subscribeToAll(Long chatId) {
        log.info("Подписка на все типы: chatId={}", chatId);
        return userNotificationService.subscribeToAllT(chatId);
    }

    @Override
    public void unsubscribeFromAll(Long chatId) {
        log.info("Отписка от всех типов: chatId={}", chatId);
        userNotificationService.unsubscribeFromAllT(chatId);
    }



}

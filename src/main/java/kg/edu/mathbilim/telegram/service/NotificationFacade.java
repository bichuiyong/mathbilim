package kg.edu.mathbilim.telegram.service;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;
import kg.edu.mathbilim.model.notifications.UserNotification;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.notification.NotificationRepository;
import kg.edu.mathbilim.service.interfaces.notification.NotificationTypeService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationFacade {

    private final UserNotificationService userNotificationService;
    private final TelegramNotificationService telegramNotificationService;
    private final NotificationTypeService notificationTypeService;
    private final NotificationRepository notificationRepository;

    public void subscribe(NotificationEnum notificationType, Long chatId) {
        log.info("Подписка пользователя с chatId {} на уведомления типа {}", chatId, notificationType);
        userNotificationService.subscribe(notificationType, chatId);
    }

    public void unsubscribe(NotificationEnum notificationType, Long chatId) {
        log.info("Отписка пользователя с chatId {} от уведомлений типа {}", chatId, notificationType);
        userNotificationService.unsubscribe(notificationType, chatId);
    }

    public List<String> getAllSubscribedUsersType(Long chatId) {
        return userNotificationService.getAllSubscribedUsersType(chatId);
    }


    @Transactional(readOnly = true)
    public void notifyAllSubscribed(NotificationEnum type, NotificationData notificationData) {
        NotificationType notificationType = notificationTypeService.findByName(type);
        List<UserNotification> subscriptions = notificationRepository
                .findByTypeWithUserNative(notificationType.getId());

        String description = notificationData.getDescription();
        if (description != null) {
            description = description.replaceAll("Powered by Froala Editor", "").trim();
            notificationData.setDescription(description);
        }

        log.info("Начинается рассылка уведомлений типа '{}' для {} подписчиков",
                type, subscriptions.size());
        log.info("Данные уведомления: title='{}', description='{}', mainImageId={}",
                notificationData.getTitle(),
                description != null ?
                        description.substring(0, Math.min(50, description.length())) + "..." : "null",
                notificationData.getMainImageId());

        int successCount = 0;
        int errorCount = 0;

        for (UserNotification sub : subscriptions) {
            try {
                Long telegramId = sub.getUser().getTelegramId();
                if (telegramId == null) {
                    log.warn("У пользователя {} отсутствует Telegram ID", sub.getUser().getName());
                    continue;
                }

                telegramNotificationService.sendNotificationToUser(telegramId, type, notificationData);
                successCount++;
                log.debug("Уведомление отправлено: {}", sub.getUser().getName());
            } catch (Exception e) {
                errorCount++;
                log.error("Ошибка отправки для {}: {}", sub.getUser().getName(), e.getMessage());
            }
        }

        log.info("Рассылка завершена. Успешно: {}, Ошибок: {}", successCount, errorCount);
    }


    public void notifyUser(Long telegramId, NotificationEnum type, NotificationData notificationData) {
        log.info("Отправка уведомления пользователю с telegramId: {}", telegramId);
        try {
            telegramNotificationService.sendNotificationToUser(telegramId, type, notificationData);
            log.info("Уведомление успешно отправлено пользователю {}", telegramId);
        } catch (Exception e) {
            log.error("Ошибка отправки уведомления пользователю {}: {}", telegramId, e.getMessage());
            throw e;
        }
    }


    public void sendPhotoFromDatabase(Long chatId, Long fileId, String caption) {
        log.info("Отправка фото из БД пользователю {}: fileId={}", chatId, fileId);
        telegramNotificationService.sendPhotoFromDatabase(chatId, fileId, caption);
    }

}
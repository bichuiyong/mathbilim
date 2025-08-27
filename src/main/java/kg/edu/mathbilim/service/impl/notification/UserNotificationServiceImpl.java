package kg.edu.mathbilim.service.impl.notification;

import jakarta.annotation.PostConstruct;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.mapper.user.UserMapper;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;
import kg.edu.mathbilim.model.notifications.UserNotification;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.notification.NotificationRepository;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.notification.NotificationTypeService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserNotificationServiceImpl implements UserNotificationService {

    @Lazy
    private final UserService userService;
    private final NotificationRepository userNotificationRepository;
    private final UserMapper userMapper;
    private final NotificationTypeService notificationTypeService;

    @PostConstruct
    public void checkService() {
        log.info("UserNotificationService успешно инициализирован");
    }

    @Override
    public void subscribe(NotificationEnum notificationType) {
        UserDto userDto = userService.getAuthUser();
        NotificationType notification = notificationTypeService.findByName(notificationType);

        boolean alreadySubscribed = userNotificationRepository.existsByUserIdAndTypeId(userDto.getId(), notification.getId());
        if (!alreadySubscribed) {
            UserNotification subscription = new UserNotification();
            subscription.setUser(userMapper.toEntity(userDto));
            subscription.setType(notification);
            userNotificationRepository.save(subscription);
        }
    }

    @Override
    public boolean subscribe(NotificationEnum notificationType, Long chatId) {
        log.info("Получен запрос на подписку. Тип: {}, Chat ID: {}", notificationType, chatId);

        User userDto = userService.findByTelegramId(String.valueOf(chatId));
        log.info("Пользователь найден: {}", userDto.getName());

        NotificationType notification = notificationTypeService.findByName(notificationType);
        log.info("Тип уведомления: {}", notification.getName());

        boolean alreadySubscribed = userNotificationRepository.existsByUserIdAndTypeId(userDto.getId(), notification.getId());

        if (alreadySubscribed) {
            log.info("Подписка уже существует для пользователя {} на тип {}", userDto.getName(), notification.getName());
            return false; // Уже подписан
        }

        UserNotification subscription = new UserNotification();
        subscription.setUser(userDto);
        subscription.setType(notification);
        userNotificationRepository.save(subscription);
        log.info("Новая подписка создана: {} -> {}", userDto.getName(), notification.getName());

        return true; // Новая подписка создана
    }

    @Override
    public void unsubscribe(NotificationEnum notificationType) {
        UserDto userDto = userService.getAuthUser();
        NotificationType notification = notificationTypeService.findByName(notificationType);
        UserNotification userNotification = userNotificationRepository
                .findByUserIdAndTypeId(userDto.getId(), notification.getId())
                .orElseThrow(NoSuchElementException::new);
        userNotificationRepository.delete(userNotification);
    }

    @Override
    public boolean unsubscribe(NotificationEnum notificationType, Long chatId) {
        log.info("Получен запрос на отписку. Тип: {}, Chat ID: {}", notificationType, chatId);

        User userDto = userService.findByTelegramId(String.valueOf(chatId));
        NotificationType notification = notificationTypeService.findByName(notificationType);

        Optional<UserNotification> userNotification = userNotificationRepository
                .findByUserIdAndTypeId(userDto.getId(), notification.getId());

        if (userNotification.isEmpty()) {
            log.info("Пользователь {} не подписан на тип {}", userDto.getName(), notification.getName());
            return false; // Не был подписан
        }

        userNotificationRepository.delete(userNotification.get());
        log.info("Отписка выполнена: {} -> {}", userDto.getName(), notification.getName());

        return true; // Отписка выполнена
    }

    @Override
    public boolean isSubscribed(NotificationEnum notificationType) {
        UserDto userDto = userService.getAuthUser();
        NotificationType notification = notificationTypeService.findByName(notificationType);
        return userNotificationRepository.existsByUserIdAndTypeId(userDto.getId(), notification.getId());
    }

    @Override
    public List<String> getAllSubscribedUsersType(Long chatId) {
        User user = userService.findByTelegramId(String.valueOf(chatId));
        List<UserNotification> notifications = userNotificationRepository.findByUserId(user.getId());

        return notifications.stream()
                .map(n -> n.getType().getName().name())
                .collect(Collectors.toList());
    }

    @Override
    public int subscribeToAllT(Long chatId) {
        log.info("Подписка на все типы уведомлений для Chat ID: {}", chatId);

        User user = userService.findByTelegramId(String.valueOf(chatId));
        return subscribeToAllInternal(user.getTelegramId());
    }

    private int subscribeToAllInternal(Long telegramChatId) {
        log.info("Начинаем подписку на все уведомления для Telegram Chat ID: {}", telegramChatId);

        User user;
        try {
            log.debug("Поиск пользователя по Telegram Chat ID: {}", telegramChatId);
            user = userService.findByTelegramId(String.valueOf(telegramChatId));

            if (user == null) {
                log.error("Пользователь не найден по Telegram Chat ID: {}", telegramChatId);
                return 0;
            }

            log.info("Найден пользователь: ID={}, имя={}", user.getId(), user.getName());

        } catch (Exception e) {
            log.error("Ошибка при поиске пользователя по Telegram Chat ID {}: {}", telegramChatId, e.getMessage(), e);
            return 0;
        }

        try {
            log.debug("Получение существующих подписок для пользователя ID: {}", user.getId());
            List<UserNotification> existingSubscriptions = userNotificationRepository.findByUserId(user.getId());
            log.info("Найдено {} существующих подписок", existingSubscriptions.size());

            List<Integer> existingTypeIds = existingSubscriptions.stream()
                    .map(subscription -> subscription.getType().getId())
                    .collect(Collectors.toList());
            log.debug("Существующие типы подписок: {}", existingTypeIds);

        } catch (Exception e) {
            log.error("Ошибка при получении существующих подписок для пользователя {}: {}", user.getId(), e.getMessage(), e);
            return 0;
        }

        int subscriptionsAdded = 0;
        int subscriptionsAlreadyExist = 0;
        int subscriptionsErrors = 0;

        log.info("Начинаем обработку всех типов уведомлений. Всего типов: {}", NotificationEnum.values().length);

        for (NotificationEnum notificationType : NotificationEnum.values()) {
            log.debug("Обработка типа уведомления: {}", notificationType);

            try {
                NotificationType notification = notificationTypeService.findByName(notificationType);

                if (notification == null) {
                    log.warn("Тип уведомления {} не найден в базе данных", notificationType);
                    subscriptionsErrors++;
                    continue;
                }

                log.debug("Найден тип уведомления: ID={}, название={}", notification.getId(), notification.getName());

                boolean alreadySubscribed = userNotificationRepository.existsByUserIdAndTypeId(user.getId(), notification.getId());

                if (alreadySubscribed) {
                    subscriptionsAlreadyExist++;
                    log.debug("Подписка на {} уже существует", notificationType);
                    continue;
                }

                UserNotification subscription = new UserNotification();
                subscription.setUser(user);
                subscription.setType(notification);

                log.debug("Создаем новую подписку: пользователь={}, тип={}", user.getId(), notification.getId());
                userNotificationRepository.save(subscription);

                subscriptionsAdded++;
                log.info("Успешно добавлена подписка на: {} (ID={})", notificationType, subscription.getId());

            } catch (Exception e) {
                log.error("Ошибка при создании подписки на тип {}: {}", notificationType, e.getMessage(), e);
                subscriptionsErrors++;
            }
        }

        log.info("Завершена подписка для пользователя {}. Добавлено: {}, уже существовало: {}, ошибок: {}",
                telegramChatId, subscriptionsAdded, subscriptionsAlreadyExist, subscriptionsErrors);

        if (subscriptionsAdded == 0 && subscriptionsErrors == 0) {
            log.info("Все подписки уже были активны для пользователя {}", telegramChatId);
        } else if (subscriptionsErrors > 0) {
            log.warn("Подписка завершена с ошибками. Успешных: {}, ошибок: {}", subscriptionsAdded, subscriptionsErrors);
        } else {
            log.info("Подписка успешно завершена. Новых подписок: {}", subscriptionsAdded);
        }

        return subscriptionsAdded;
    }

    @Override
    public void unsubscribeFromAllT(Long chatId) {
        log.info("Отписка от всех типов уведомлений для Chat ID: {}", chatId);

        User user = userService.findByTelegramId(String.valueOf(chatId));
        unsubscribeFromAll(user.getId());
    }

    private void unsubscribeFromAll(Long userId) {
        log.info("Отписка пользователя {} от всех типов уведомлений", userId);

        List<UserNotification> userSubscriptions = userNotificationRepository.findByUserId(userId);

        if (userSubscriptions.isEmpty()) {
            log.info("У пользователя {} нет активных подписок", userId);
            return;
        }

        userNotificationRepository.deleteAll(userSubscriptions);

        log.info("Удалено {} подписок для пользователя {}", userSubscriptions.size(), userId);
    }
}
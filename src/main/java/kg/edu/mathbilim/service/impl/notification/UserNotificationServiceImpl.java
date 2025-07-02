package kg.edu.mathbilim.service.impl.notification;

import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.mapper.user.UserMapper;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;
import kg.edu.mathbilim.model.notifications.UserNotification;
import kg.edu.mathbilim.repository.notification.NotificationRepository;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.notification.NotificationTypeService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserNotificationServiceImpl implements UserNotificationService {
    private final UserService userService;
    private final NotificationRepository userNotificationRepository;
    private final UserMapper userMapper;
    private final kg.edu.mathbilim.telegram.bot.TelegramBot telegramBot;
    private final NotificationTypeService notificationTypeService;

    @Override

    public void subscribe( NotificationEnum notificationType) {
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
    @Transactional(readOnly = true)
    public void notifyAllSubscribed(NotificationEnum type, String message)  {
        NotificationType notificationType = notificationTypeService.findByName(type);
        List<UserNotification> subscriptions = userNotificationRepository
                .findByTypeWithUserNative(notificationType.getId());
        for (UserNotification sub : subscriptions) {
            Long telegramId = sub.getUser().getTelegramId();
            if (telegramId != null) {
                SendMessage msg = new SendMessage();
                msg.setChatId(String.valueOf(telegramId));
                msg.setText(message);
                try {
                    telegramBot.execute(msg);
                }catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void unsubscribe(NotificationEnum notificationType) {
        UserDto userDto = userService.getAuthUser();
        NotificationType notification = notificationTypeService.findByName(notificationType);
        UserNotification userNotification = userNotificationRepository.findByUserIdAndTypeId(userDto.getId(), notification.getId()).orElseThrow(NoSuchElementException::new);
        userNotificationRepository.delete(userNotification);
    }

    @Override
    public boolean isSubscribed(NotificationEnum notificationType) {
        UserDto userDto = userService.getAuthUser();
        NotificationType notification = notificationTypeService.findByName(notificationType);
        return userNotificationRepository.existsByUserIdAndTypeId(userDto.getId(), notification.getId());
    }
}

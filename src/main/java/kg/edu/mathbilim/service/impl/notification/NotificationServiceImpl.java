package kg.edu.mathbilim.service.impl.notification;

import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.mapper.user.UserMapper;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;
import kg.edu.mathbilim.model.notifications.UserNotification;
import kg.edu.mathbilim.repository.notification.NotificationRepository;
import kg.edu.mathbilim.repository.notification.NotificationTypeRepository;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final UserService userService;
    private final NotificationRepository userNotificationRepository;
    private final UserMapper userMapper;
    private final NotificationTypeRepository notificationTypeRepository;

    @Override

    public void subscribe(String email, NotificationEnum notificationType) {
        UserDto userDto = userService.getUserByEmail(email);
        NotificationType notification = notificationTypeRepository.findByName(notificationType).orElseThrow(()-> new NoSuchElementException("No such type"));

        boolean alreadySubscribed = userNotificationRepository.existsByUserIdAndTypeId(userDto.getId(), notification.getId());
        if (!alreadySubscribed) {
            UserNotification subscription = new UserNotification();
            subscription.setUser(userMapper.toEntity(userDto));
            subscription.setType(notification);
            userNotificationRepository.save(subscription);
        }


    }

    @Override
    public void notifyAllSubscribed(NotificationType type, String message, TelegramBot bot) {
        List<UserNotification> subscriptions = userNotificationRepository.findByType(type);
        for (UserNotification sub : subscriptions) {
            Long telegramId = sub.getUser().getTelegramId();
            if (telegramId != null) {
                SendMessage msg = new SendMessage();
                msg.setChatId(String.valueOf(telegramId));
                msg.setText(message);
//                bot.execute(msg);
            }
        }
    }
}

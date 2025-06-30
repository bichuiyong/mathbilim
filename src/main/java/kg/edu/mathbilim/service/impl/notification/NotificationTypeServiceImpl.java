package kg.edu.mathbilim.service.impl.notification;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;
import kg.edu.mathbilim.repository.notification.NotificationTypeRepository;
import kg.edu.mathbilim.service.interfaces.notification.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NotificationTypeServiceImpl implements NotificationTypeService {
    private final NotificationTypeRepository notificationTypeRepository;

    @Override
    public NotificationType findByName(NotificationEnum name) {
        return notificationTypeRepository.findByName(name)
                .orElseThrow(
                        ()-> new NoSuchElementException(String.format("Notification type with name '%s' not found", name))
                );
    }
}

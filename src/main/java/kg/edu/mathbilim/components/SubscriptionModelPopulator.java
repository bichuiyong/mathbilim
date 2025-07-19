package kg.edu.mathbilim.components;

import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
@RequiredArgsConstructor
public class SubscriptionModelPopulator {

    private final UserNotificationService userNotificationService;

    public  void addSubscriptionAttributes(Authentication authentication,
                                          NotificationEnum notificationType,
                                          Model model) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        boolean isSubscribed = false;

        if (isAuthenticated) {
            isSubscribed = userNotificationService.isSubscribed(notificationType);
        }

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isBlogSubscribed", isSubscribed);
    }
}

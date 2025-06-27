package kg.edu.mathbilim.controller.mvc;

import jakarta.servlet.http.HttpServletRequest;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.notifications.NotificationType;
import kg.edu.mathbilim.service.interfaces.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/subscribe")
    public String subscribe(
            @RequestParam("type") NotificationEnum type,
            Authentication principal,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        try {
            notificationService.subscribe(principal.getName(), type);
            redirectAttributes.addFlashAttribute("message", "Вы подписались на уведомления!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("warning", "Вы уже подписаны.");
        }
        return "redirect:" + request.getHeader("Referer");
    }

}

package kg.edu.mathbilim.telegram.service;

import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.telegram.bot.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramNotificationService {

    private final TelegramBot telegramBot;
    private final UserService userService;

    public void sendVisitNotificationToAll() {
        List<Long> chatIds = userService.getSubscribedChatIds();
        for (Long chatId : chatIds) {
            sendVisitNotification(chatId);
        }
    }

    public void sendVisitNotification(Long chatId) {
        SendAnimation animation = new SendAnimation();
        animation.setChatId(chatId.toString());

        animation.setAnimation(new InputFile("https://media.giphy.com/media/3o7TKtd1LXe5C6TgYM/giphy.gif"));

        animation.setCaption("⚡ Кто-то только что зашёл на сайт!");
        try {
            telegramBot.execute(animation);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки гиф-уведомления пользователю {}: {}", chatId, e.getMessage());
        }
    }
}

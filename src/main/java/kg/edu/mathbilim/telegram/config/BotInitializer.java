package kg.edu.mathbilim.telegram.config;

import jakarta.annotation.PostConstruct;
import kg.edu.mathbilim.telegram.bot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
public class BotInitializer {

    private final TelegramBot myTelegramBot;

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(myTelegramBot);
            System.out.println("✅ Бот успешно зарегистрирован");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

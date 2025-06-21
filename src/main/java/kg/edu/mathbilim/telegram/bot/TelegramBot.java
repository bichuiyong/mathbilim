package kg.edu.mathbilim.telegram.bot;

import jakarta.annotation.PostConstruct;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.telegram.config.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserService userService;

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    private ReplyKeyboardMarkup mainMenuKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("🔔 Подписаться");
        row1.add("❌ Отписаться");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("📋 Мои задачи");

        keyboard.setKeyboard(List.of(row1, row2));
        return keyboard;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!(update.hasMessage() && update.getMessage().hasText())) {
            return;
        }

        String chatId = update.getMessage().getChatId().toString();
        String userMessage = update.getMessage().getText();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        try {
            if (userMessage.startsWith("/start")) {
                handleStartCommand(userMessage, chatId, message);
            } else {
                handleUserMessage(userMessage, chatId, message);
            }
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения: ", e);
        }
    }

    private void handleStartCommand(String userMessage, String chatId, SendMessage message) {
        String[] parts = userMessage.split(" ");
        if (parts.length > 1) {
            boolean registered = registerChatId(parts[1], chatId);
            if (registered) {
                message.setText("✅ Уведомления подключены!\nВаш chat ID: " + chatId);
            } else {
                if (userService.isSubscribed(Long.parseLong(chatId))) {
                    message.setText("⚠️ Вы уже подключены к уведомлениям.");
                } else {
                    message.setText("⚠️ Вы зарегистрированы, но уведомления выключены. Нажмите 🔔 Подписаться для включения.");
                }
            }
        } else {
            boolean subscribed = userService.isSubscribed(Long.parseLong(chatId));
            String baseMessage = "👋 Добро пожаловать! Выберите действие:\n";
            if (subscribed) {
                message.setText(baseMessage + "Вы подписаны на уведомления.");
            } else {
                message.setText(baseMessage + "Вы не подписаны на уведомления. Нажмите 🔔 Подписаться.");
            }
        }
        message.setReplyMarkup(mainMenuKeyboard());
    }

    private void handleUserMessage(String userMessage, String chatId, SendMessage message) {
        switch (userMessage) {
            case "/help" -> message.setText("""
                ℹ️ Справка по командам:
                /start — запуск бота
                /help — справка
                /id — узнать свой Chat ID
                /menu — показать кнопки
                """);
            case "/id" -> message.setText("🆔 Ваш chat ID: " + chatId);
            case "/menu" -> {
                message.setText("📋 Главное меню:");
                message.setReplyMarkup(mainMenuKeyboard());
            }
            case "🔔 Подписаться" -> {
                subscribe(chatId);
                message.setText("✅ Вы подписались на уведомления!");
            }
            case "❌ Отписаться" -> {
                unsubscribe(chatId);
                message.setText("🚫 Вы отписались от уведомлений.");
            }
            default -> message.setText("❓ Неизвестная команда: " + userMessage);
        }
    }

    @PostConstruct
    public void initCommands() {
        List<BotCommand> commandList = List.of(
                new BotCommand("/start", "Запустить бота"),
                new BotCommand("/help", "Помощь по командам"),
                new BotCommand("/id", "Узнать свой chat ID"),
                new BotCommand("/menu", "Показать главное меню")
        );

        try {
            execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
            log.info("✅ Команды успешно установлены.");
        } catch (TelegramApiException e) {
            log.error("⚠️ Ошибка установки команд:", e);
        }
    }

    private boolean registerChatId(String userId, String chatId) {
        try {
            Long user = Long.parseLong(userId);
            Long chat = Long.parseLong(chatId);

            if (userService.hasChatId(user)) {
                return false;
            }

            userService.registerChatId(user, chat);
            return true;
        } catch (Exception e) {
            log.error("Ошибка при сохранении chatId", e);
            return false;
        }
    }

    private void unsubscribe(String chatId) {
        try {
            userService.unsubscribe(Long.parseLong(chatId));
        } catch (Exception e) {
            log.warn("Ошибка при отписке пользователя: chatId=" + chatId, e);
        }
    }

    private void subscribe(String chatId) {
        try {
            userService.subscribe(Long.parseLong(chatId));
        } catch (Exception e) {
            log.warn("Ошибка при подписке пользователя: chatId=" + chatId, e);
        }
    }
}

package kg.edu.mathbilim.telegram.bot;

import jakarta.annotation.PostConstruct;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.telegram.config.BotConfig;
import kg.edu.mathbilim.telegram.enumTelegram.SubscriptionResult;
import kg.edu.mathbilim.telegram.service.TelegramUserService;
import kg.edu.mathbilim.telegram.enumTelegram.UnsubscriptionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserService userService;
    private final TelegramUserService telegramUserService;


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
        keyboard.setOneTimeKeyboard(false);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("🔔 Управление подписками");
        row1.add("📊 Мои подписки");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("❌ Быстрая отписка");
        row2.add("⚙️ Настройки");

        keyboard.setKeyboard(List.of(row1, row2));
        return keyboard;
    }

    private InlineKeyboardMarkup subscriptionManagementKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(createInlineButton("🏆 Олимпиады", "sub_OLYMPIAD"));
        row1.add(createInlineButton("📰 Новости", "sub_NEWS"));
        rows.add(row1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(createInlineButton("📝 Публикации", "sub_POST"));
        row2.add(createInlineButton("📖 Блоги", "sub_BLOG"));
        rows.add(row2);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(createInlineButton("🎉 Мероприятия", "sub_EVENT"));
        rows.add(row3);

        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(createInlineButton("✅ Подписаться на все", "sub_all"));
        row4.add(createInlineButton("❌ Отписаться от всех", "unsub_all"));
        rows.add(row4);

        List<InlineKeyboardButton> row5 = new ArrayList<>();
        row5.add(createInlineButton("🔙 Назад в меню", "back_to_main"));
        rows.add(row5);

        markup.setKeyboard(rows);
        return markup;
    }

    // Новое меню для быстрой отписки
    private InlineKeyboardMarkup quickUnsubscribeKeyboard(List<String> subscriptions) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        if (!subscriptions.isEmpty()) {
            // Заголовок
            List<InlineKeyboardButton> headerRow = new ArrayList<>();
            headerRow.add(createInlineButton("❌ Выберите для отписки:", "header"));
            rows.add(headerRow);

            // Активные подписки для отписки
            for (String subscription : subscriptions) {
                List<InlineKeyboardButton> row = new ArrayList<>();
                String emoji = getSubscriptionEmoji(subscription);
                String name = getSubscriptionName(subscription);
                row.add(createInlineButton(emoji + " " + name + " ❌", "quick_unsub_" + subscription));
                rows.add(row);
            }

            // Разделитель
            List<InlineKeyboardButton> dividerRow = new ArrayList<>();
            dividerRow.add(createInlineButton("━━━━━━━━━━━━━━", "divider"));
            rows.add(dividerRow);
        } else {
            List<InlineKeyboardButton> noSubsRow = new ArrayList<>();
            noSubsRow.add(createInlineButton("ℹ️ Нет активных подписок", "no_subs"));
            rows.add(noSubsRow);
        }

        // Управляющие кнопки
        List<InlineKeyboardButton> controlRow = new ArrayList<>();
        controlRow.add(createInlineButton("🔄 Обновить", "refresh_quick_unsub"));
        controlRow.add(createInlineButton("❌ Отписаться от всех", "confirm_unsub_all"));
        rows.add(controlRow);

        List<InlineKeyboardButton> backRow = new ArrayList<>();
        backRow.add(createInlineButton("🔙 Назад в меню", "back_to_main"));
        rows.add(backRow);

        markup.setKeyboard(rows);
        return markup;
    }

    // Меню подтверждения отписки от всех
    private InlineKeyboardMarkup confirmUnsubscribeAllKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> confirmRow = new ArrayList<>();
        confirmRow.add(createInlineButton("✅ Да, отписаться", "execute_unsub_all"));
        confirmRow.add(createInlineButton("❌ Отмена", "cancel_unsub_all"));
        rows.add(confirmRow);

        List<InlineKeyboardButton> backRow = new ArrayList<>();
        backRow.add(createInlineButton("🔙 Назад", "quick_unsubscribe"));
        rows.add(backRow);

        markup.setKeyboard(rows);
        return markup;
    }

    // Меню настроек
    private InlineKeyboardMarkup settingsKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(createInlineButton("🔔 Управление подписками", "manage_subscriptions"));
        rows.add(row1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(createInlineButton("❌ Быстрая отписка", "quick_unsubscribe"));
        rows.add(row2);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(createInlineButton("📊 Статистика подписок", "subscription_stats"));
        rows.add(row3);

        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(createInlineButton("🆔 Мой Chat ID", "show_chat_id"));
        rows.add(row4);

        List<InlineKeyboardButton> backRow = new ArrayList<>();
        backRow.add(createInlineButton("🔙 Главное меню", "back_to_main"));
        rows.add(backRow);

        markup.setKeyboard(rows);
        return markup;
    }

    private InlineKeyboardMarkup mySubscriptionsKeyboard(List<String> subscriptions) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        if (!subscriptions.isEmpty()) {
            for (String subscription : subscriptions) {
                List<InlineKeyboardButton> row = new ArrayList<>();
                String emoji = getSubscriptionEmoji(subscription);
                String name = getSubscriptionName(subscription);
                row.add(createInlineButton(emoji + " " + name + " ✅", "unsub_" + subscription));
                rows.add(row);
            }
        }

        List<InlineKeyboardButton> controlRow1 = new ArrayList<>();
        controlRow1.add(createInlineButton("🔄 Обновить", "refresh_subs"));
        controlRow1.add(createInlineButton("➕ Добавить", "add_subscription"));
        rows.add(controlRow1);

        List<InlineKeyboardButton> controlRow2 = new ArrayList<>();
        controlRow2.add(createInlineButton("❌ Быстрая отписка", "quick_unsubscribe"));
        controlRow2.add(createInlineButton("🔙 Главное меню", "back_to_main"));
        rows.add(controlRow2);

        markup.setKeyboard(rows);
        return markup;
    }

    private InlineKeyboardButton createInlineButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    private String getSubscriptionEmoji(String subscription) {
        return switch (subscription.toUpperCase()) {
            case "OLYMPIAD" -> "🏆";
            case "NEWS" -> "📰";
            case "POST" -> "📝";
            case "BLOG" -> "📖";
            case "EVENT" -> "🎉";
            default -> "📌";
        };
    }

    private String getSubscriptionName(String subscription) {
        return switch (subscription.toUpperCase()) {
            case "OLYMPIAD" -> "Олимпиады";
            case "NEWS" -> "Новости";
            case "POST" -> "Публикации";
            case "BLOG" -> "Блоги";
            case "EVENT" -> "Мероприятия";
            default -> "Неизвестная подписка";
        };
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                handleTextMessage(update);
            } else if (update.hasCallbackQuery()) {
                handleCallbackQuery(update);
            }
        } catch (Exception e) {
            log.error("Общая ошибка при обработке обновления: ", e);
        }
    }

    private void handleTextMessage(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String userMessage = update.getMessage().getText();

        log.info("📥 Получено текстовое сообщение: chatId={}, message={}", chatId, userMessage);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode("HTML");

        try {
            if (userMessage.startsWith("/start")) {
                handleStartCommand(userMessage, chatId, message);
            } else {
                handleUserMessage(userMessage, chatId, message);
            }

            if (message.getText() != null && !message.getText().isEmpty()) {
                log.info("📤 Отправляем ответ: chatId={}, response length={}", chatId, message.getText().length());
                execute(message);
            } else {
                log.warn("⚠️ Пустое сообщение для отправки: chatId={}", chatId);
            }
        } catch (TelegramApiException e) {
            log.error("❌ Ошибка при отправке сообщения: chatId={}", chatId, e);
        }
    }

    private void handleCallbackQuery(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        String callbackData = update.getCallbackQuery().getData();

        log.info("🔘 Получен callback: chatId={}, data={}", chatId, callbackData);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode("HTML");

        try {
            handleCallbackData(callbackData, chatId, message);
            if (message.getText() != null && !message.getText().isEmpty()) {
                log.info("📤 Отправляем ответ на callback: chatId={}, response length={}", chatId, message.getText().length());
                execute(message);
            } else {
                log.warn("⚠️ Пустое сообщение для отправки в callback: chatId={}", chatId);
            }
        } catch (TelegramApiException e) {
            log.error("❌ Ошибка при обработке callback: chatId={}, data={}", chatId, callbackData, e);
        }
    }

    private void handleCallbackData(String callbackData, String chatId, SendMessage message) {
        log.info("🔄 Обработка callback data: {}", callbackData);

        switch (callbackData) {
            case "add_subscription", "manage_subscriptions" -> {
                message.setText(createSubscriptionManagementText());
                message.setReplyMarkup(subscriptionManagementKeyboard());
            }
            case "quick_unsubscribe" -> {
                showQuickUnsubscribe(chatId, message);
            }
            case "refresh_quick_unsub" -> {
                showQuickUnsubscribe(chatId, message);
            }
            case "confirm_unsub_all" -> {
                message.setText(createConfirmUnsubscribeAllText());
                message.setReplyMarkup(confirmUnsubscribeAllKeyboard());
            }
            case "execute_unsub_all" -> {
                unsubscribeFromAll(chatId);
                message.setText(createUnsubscriptionText("всех уведомлений"));
                showQuickUnsubscribe(chatId, message);
            }
            case "cancel_unsub_all" -> {
                showQuickUnsubscribe(chatId, message);
            }
            case "settings" -> {
                message.setText(createSettingsText());
                message.setReplyMarkup(settingsKeyboard());
            }
            case "subscription_stats" -> {
                showSubscriptionStats(chatId, message);
            }
            case "show_chat_id" -> {
                message.setText(createChatIdText(chatId));
                message.setReplyMarkup(settingsKeyboard());
            }
            case "back_to_main" -> {
                message.setText(createMainMenuText());
                message.setReplyMarkup(mainMenuKeyboard());
            }
            case "header", "divider", "no_subs" -> {
                // Игнорируем клики по неактивным кнопкам
                return;
            }
            default -> {
                if (callbackData.startsWith("sub_")) {
                    handleSubscriptionCallback(callbackData, chatId, message);
                } else if (callbackData.startsWith("unsub_")) {
                    handleUnsubscriptionCallback(callbackData, chatId, message);
                } else if (callbackData.startsWith("quick_unsub_")) {
                    handleQuickUnsubscriptionCallback(callbackData, chatId, message);
                } else if (callbackData.equals("refresh_subs")) {
                    showMySubscriptions(chatId, message);
                } else {
                    log.warn("⚠️ Неизвестный callback data: {}", callbackData);
                }
            }
        }
    }


    private void handleStartCommand(String userMessage, String chatId, SendMessage message) {
        log.info("🚀 Обработка команды start: chatId={}, message={}", chatId, userMessage);

        String[] parts = userMessage.split(" ");
        if (parts.length > 1) {
            boolean registered = registerChatId(parts[1], chatId);
            if (registered) {
                message.setText(createWelcomeRegisteredText(chatId));
                message.setReplyMarkup(mainMenuKeyboard());
            } else {
                if (userService.hasChatId(Long.parseLong(chatId))) {
                    message.setText(createAlreadyConnectedText());
                } else {
                    message.setText(createRegisteredButDisabledText());
                }
                message.setReplyMarkup(mainMenuKeyboard());
            }
        } else {
            // Нет userId - показываем предупреждение
            message.setText(createWelcomeText());
            // Не добавляем главное меню, так как пользователь не авторизован
        }
    }

    private void handleUserMessage(String userMessage, String chatId, SendMessage message) {
        log.info("💬 Обработка пользовательского сообщения: chatId={}, message='{}'", chatId, userMessage);

        if (!isUserAuthorized(chatId)) {
            log.warn("🚫 Неавторизованный пользователь: chatId={}", chatId);
            message.setText(createUnauthorizedMessage());
            return;
        }

        // Обрезаем пробелы и нормализуем сообщение
        String normalizedMessage = userMessage.trim();

        switch (normalizedMessage) {
            case "🔔 Управление подписками" -> {
                log.info("🔔 Открываем управление подписками: chatId={}", chatId);
                message.setText(createSubscriptionManagementText());
                message.setReplyMarkup(subscriptionManagementKeyboard());
            }

            case "📊 Мои подписки" -> {
                log.info("📊 Показываем подписки пользователя: chatId={}", chatId);
                showMySubscriptions(chatId, message);
            }

            case "❌ Быстрая отписка" -> {
                log.info("⚡ Открываем быструю отписку: chatId={}", chatId);
                showQuickUnsubscribe(chatId, message);
            }

            case "⚙️ Настройки" -> {
                log.info("⚙️ Открываем настройки: chatId={}", chatId);
                message.setText(createSettingsText());
                message.setReplyMarkup(settingsKeyboard());
            }

            default -> {
                log.warn("❓ Неизвестная команда: chatId={}, message='{}'", chatId, normalizedMessage);
                message.setText(createUnknownCommandText(normalizedMessage));
            }
        }
    }

    private boolean isUserAuthorized(String chatId) {
        try {
            boolean authorized = userService.hasChatId(Long.parseLong(chatId));
            log.info("🔐 Проверка авторизации: chatId={}, authorized={}", chatId, authorized);
            return authorized;
        } catch (Exception e) {
            log.error("❌ Ошибка при проверке авторизации: chatId={}", chatId, e);
            return false;
        }
    }

    // Новые методы для создания текстов

    private String createMainMenuText() {
        return """
                🏠 <b>Главное меню MathBilim Bot</b>
                
                🎯 <b>Доступные функции:</b>
                
                🔔 <b>Управление подписками</b> - настройка уведомлений
                📊 <b>Мои подписки</b> - просмотр активных подписок
                ❌ <b>Быстрая отписка</b> - быстрое отключение уведомлений
                ⚙️ <b>Настройки</b> - дополнительные опции
                
                💡 <i>Выберите нужное действие из меню</i>
                """;
    }

    private String createQuickUnsubscribeText(List<String> subscriptions) {
        if (subscriptions.isEmpty()) {
            return """
                    ❌ <b>Быстрая отписка</b>
                    
                    ℹ️ <b>Нет активных подписок</b>
                    
                    🔕 У вас нет подписок для отмены
                    
                    💡 <b>Что можно сделать:</b>
                    • Вернуться в главное меню
                    • Настроить новые подписки
                    
                    🔔 <i>Используйте 'Управление подписками' для настройки</i>
                    """;
        }

        StringBuilder text = new StringBuilder();
        text.append("❌ <b>Быстрая отписка</b>\n\n");
        text.append("⚡ <b>Отключение уведомлений одним нажатием</b>\n\n");
        text.append("✅ <b>Ваши активные подписки (").append(subscriptions.size()).append("):</b>\n");

        for (String sub : subscriptions) {
            String emoji = getSubscriptionEmoji(sub);
            String name = getSubscriptionName(sub);
            text.append("• ").append(emoji).append(" ").append(name).append("\n");
        }

        text.append("\n💡 <i>Нажмите на тип уведомлений для отписки</i>\n");
        text.append("⚠️ <i>Отписка произойдет мгновенно без подтверждения</i>");

        return text.toString();
    }

    private String createConfirmUnsubscribeAllText() {
        return """
                ⚠️ <b>Подтверждение отписки</b>
                
                🚨 <b>Внимание!</b>
                Вы собираетесь отписаться от <b>ВСЕХ</b> типов уведомлений.
                
                ❌ <b>Это означает:</b>
                • Прекращение всех push-уведомлений
                • Отключение уведомлений об олимпиадах
                • Отключение новостей и событий
                • Отключение уведомлений о публикациях
                
                🔄 <b>Восстановление:</b>
                Подписки можно будет восстановить через меню управления
                
                ❓ <b>Вы уверены?</b>
                """;
    }

    private String createQuickUnsubscriptionSuccessText(String subscriptionName) {
        return "⚡ <b>Быстрая отписка выполнена!</b>\n\n" +
                "❌ Отключены уведомления: <code>" + subscriptionName + "</code>\n\n" +
                "ℹ️ Изменения вступили в силу немедленно\n" +
                "🔄 Подписку можно восстановить через 'Управление подписками'";
    }

    private String createSettingsText() {
        return """
                ⚙️ <b>Настройки бота</b>
                
                🎛️ <b>Доступные опции:</b>
                
                🔔 <b>Управление подписками</b>
                   Полное управление типами уведомлений
                
                ❌ <b>Быстрая отписка</b>
                   Мгновенное отключение уведомлений
                
                📊 <b>Статистика подписок</b>
                   Детальная информация о ваших подписках
                
                🆔 <b>Мой Chat ID</b>
                   Показать ваш уникальный идентификатор
                
                💡 <i>Выберите нужную опцию</i>
                """;
    }

    private String createChatIdText(String chatId) {
        return String.format("""
                🆔 <b>Ваш Chat ID</b>
                
                📱 <b>Идентификатор:</b> <code>%s</code>
                
                ℹ️ <b>Информация:</b>
                • Уникальный номер в системе Telegram
                • Используется для отправки уведомлений
                • Привязан к вашему аккаунту MathBilim
                
                🔒 <b>Безопасность:</b>
                Не передавайте Chat ID третьим лицам
                
                💡 <i>Нажмите на номер для копирования</i>
                """, chatId);
    }

    private void showQuickUnsubscribe(String chatId, SendMessage message) {
        log.info("⚡ Показываем быструю отписку: chatId={}", chatId);

        try {
            List<String> userSubscriptions = telegramUserService.getAllSubscribedUsersType(Long.valueOf(chatId));
            log.info("📊 Получены подписки для быстрой отписки: chatId={}, subscriptions={}", chatId, userSubscriptions);

            message.setText(createQuickUnsubscribeText(userSubscriptions));
            message.setReplyMarkup(quickUnsubscribeKeyboard(userSubscriptions));

        } catch (Exception e) {
            log.error("❌ Ошибка при показе быстрой отписки: chatId={}", chatId, e);
            message.setText("❌ Произошла ошибка при загрузке данных. Попробуйте позже.");
        }
    }

    private void showSubscriptionStats(String chatId, SendMessage message) {
        log.info("📊 Показываем статистику подписок: chatId={}", chatId);

        try {
            List<String> userSubscriptions = telegramUserService.getAllSubscribedUsersType(Long.valueOf(chatId));

            StringBuilder text = new StringBuilder();
            text.append("📊 <b>Статистика подписок</b>\n\n");

            text.append("🎯 <b>Всего подписок:</b> ").append(userSubscriptions.size()).append(" из 5\n\n");

            if (!userSubscriptions.isEmpty()) {
                text.append("✅ <b>Активные подписки:</b>\n");
                for (String sub : userSubscriptions) {
                    String emoji = getSubscriptionEmoji(sub);
                    String name = getSubscriptionName(sub);
                    text.append("• ").append(emoji).append(" ").append(name).append(" ✅\n");
                }
                text.append("\n");
            }

            // Показываем неактивные подписки
            List<String> allTypes = List.of("OLYMPIAD", "NEWS", "POST", "BLOG", "EVENT");
            List<String> inactiveSubscriptions = allTypes.stream()
                    .filter(type -> !userSubscriptions.contains(type))
                    .toList();

            if (!inactiveSubscriptions.isEmpty()) {
                text.append("❌ <b>Неактивные подписки:</b>\n");
                for (String sub : inactiveSubscriptions) {
                    String emoji = getSubscriptionEmoji(sub);
                    String name = getSubscriptionName(sub);
                    text.append("• ").append(emoji).append(" ").append(name).append(" ❌\n");
                }
                text.append("\n");
            }

            // Процент активных подписок
            double percentage = (double) userSubscriptions.size() / allTypes.size() * 100;
            text.append("📈 <b>Активность:</b> ").append(String.format("%.0f", percentage)).append("%\n\n");

            if (percentage == 100) {
                text.append("🎉 <b>Отлично!</b> У вас активны все типы уведомлений");
            } else if (percentage >= 60) {
                text.append("👍 <b>Хорошо!</b> Большинство уведомлений активно");
            } else if (percentage >= 20) {
                text.append("⚠️ <b>Средне.</b> Рассмотрите активацию дополнительных типов");
            } else {
                text.append("🔕 <b>Мало подписок.</b> Активируйте уведомления для полной функциональности");
            }

            message.setText(text.toString());
            message.setReplyMarkup(settingsKeyboard());

        } catch (Exception e) {
            log.error("❌ Ошибка при показе статистики подписок: chatId={}", chatId, e);
            message.setText("❌ Произошла ошибка при загрузке статистики. Попробуйте позже.");
            message.setReplyMarkup(settingsKeyboard());
        }
    }

    private String createUnauthorizedMessage() {
        return """
                🔐 <b>Доступ ограничен!</b>
                
                Для использования бота необходимо пройти авторизацию через сайт MathBilim.
                
                <b>Как получить доступ:</b>
                1️⃣ Войдите на сайт MathBilim
                2️⃣ Перейдите в раздел "Настройки профиля"  
                3️⃣ Нажмите на кнопку "Подключить Telegram"
                4️⃣ Перейдите по персональной ссылке
                
                💡 <i>После авторизации все функции станут доступны</i>
                """;
    }

    // Методы для создания текстов
    private String createWelcomeText() {
        return """
                ⚠️ <b>Требуется авторизация!</b>
                
                🔐 Для использования бота необходимо пройти авторизацию через сайт MathBilim.
                
                <b>Как получить доступ:</b>
                1️⃣ Войдите на сайт MathBilim
                2️⃣ Перейдите в раздел "Настройки профиля"
                3️⃣ Нажмите на кнопку "Подключить Telegram"
                4️⃣ Перейдите по ссылке для активации
                
                🚀 После авторизации вам будут доступны все функции бота!
                
                💡 <i>Получите персонализированную ссылку на сайте</i>
                """;
    }

    private String createSubscriptionManagementText() {
        return """
                🔔 <b>Управление подписками</b>
                
                <b>Доступные типы уведомлений:</b>
                
                🏆 <b>Олимпиады</b>
                   Новые олимпиады и конкурсы
                
                📰 <b>Новости</b>
                   Актуальные новости образования
                
                📝 <b>Публикации</b>
                   Новые статьи и материалы
                
                📖 <b>Блоги</b>
                   Обновления блогов преподавателей
                
                🎉 <b>Мероприятия</b>
                   Семинары, конференции и события
                
                💡 <i>Выберите интересующие типы уведомлений</i>
                """;
    }

    private String createSubscriptionSuccessText(String subscriptionName) {
        return "✅ <b>Подписка оформлена!</b>\n\n" +
                "🎯 Вы успешно подписались на: <code>" + subscriptionName + "</code>\n\n" +
                "📬 Теперь вы будете получать соответствующие уведомления!";
    }

    private String createUnsubscriptionText(String subscriptionName) {
        return "🚫 <b>Отписка выполнена</b>\n\n" +
                "📤 Вы отписались от: <code>" + subscriptionName + "</code>\n\n" +
                "ℹ️ Уведомления данного типа больше не будут приходить.";
    }

    private String createWelcomeRegisteredText(String chatId) {
        return String.format("""
            ✅ <b>Добро пожаловать в MathBilim!</b> 🎓
            
            🎉 <b>Регистрация завершена!</b>
            
            🆔 <b>Ваш Chat ID:</b> <code>%s</code>
            
            <b>🚀 Что теперь доступно:</b>
            • 🔔 Персональные уведомления
            • 📊 Отслеживание подписок  
            • 🏆 Уведомления об олимпиадах
            • 📰 Новости и события
            • ❌ Быстрая отписка
            • ⚙️ Настройки бота
            
            💡 <i>Используйте меню ниже для настройки уведомлений</i>
            """, chatId);
    }

    private String createAlreadyConnectedText() {
        return """
                ⚠️ <b>Уже подключено!</b>
                
                ℹ️ <b>Информация:</b>
                ✅ Вы уже подключены к системе уведомлений MathBilim
                
                🔔 Уведомления: <b>Доступны</b>
                ⚙️ Настройки: <b>Активны</b>
                ❌ Быстрая отписка: <b>Доступна</b>
                
                💡 <i>Используйте расширенное меню для управления</i>
                """;
    }

    private String createRegisteredButDisabledText() {
        return """
                ⚠️ <b>Требуется активация!</b>
                
                📋 <b>Статус аккаунта:</b>
                ✅ Регистрация: <b>Завершена</b>
                ❌ Уведомления: <b>Выключены</b>
                
                🔧 <b>Для активации:</b>
                Используйте '🔔 Управление подписками'
                
                💡 <i>Настройте уведомления для полной функциональности</i>
                """;
    }

    private String createUnknownCommandText(String userMessage) {
        return String.format("""
            ❓ <b>Неизвестная команда</b>
            
            ⚠️ <b>Получено:</b> <code>%s</code>
            
            💡 <b>Доступные действия:</b>
            • 🔔 Управление подписками
            • 📊 Мои подписки
            • ❌ Быстрая отписка
            • ⚙️ Настройки
            
            💡 <i>Используйте кнопки меню для навигации</i>
            """, userMessage);
    }

    private void showMySubscriptions(String chatId, SendMessage message) {
        log.info("📋 Показываем подписки для пользователя: chatId={}", chatId);

        try {
            List<String> userSubscriptions = telegramUserService.getAllSubscribedUsersType(Long.valueOf(chatId));
            log.info("📊 Получены подписки: chatId={}, subscriptions={}", chatId, userSubscriptions);

            if (userSubscriptions.isEmpty()) {
                message.setText("""
                        📊 <b>Мои подписки</b>
                        
                        ❌ <b>Нет активных подписок</b>
                        
                        🔕 У вас нет активных подписок на уведомления
                        
                        💡 <b>Рекомендуем подписаться на:</b>
                        • 🏆 Олимпиады
                        • 📰 Новости  
                        • 🎉 Мероприятия
                        
                        🔔 <i>Используйте кнопки ниже для настройки</i>
                        """);
                message.setReplyMarkup(mySubscriptionsKeyboard(userSubscriptions));
            } else {
                StringBuilder text = new StringBuilder();
                text.append("📊 <b>Ваши активные подписки</b>\n\n");
                text.append("✅ <b>Активные уведомления:</b>\n\n");

                for (String sub : userSubscriptions) {
                    String emoji = getSubscriptionEmoji(sub);
                    String name = getSubscriptionName(sub);
                    text.append("• ").append(emoji).append(" <b>").append(name).append("</b> ✅\n");
                }

                text.append("\n🎯 <b>Всего подписок:</b> ").append(userSubscriptions.size()).append(" из 5\n");
                text.append("💡 <i>Нажмите на подписку для отмены или используйте быструю отписку</i>");

                message.setText(text.toString());
                message.setReplyMarkup(mySubscriptionsKeyboard(userSubscriptions));
            }
        } catch (Exception e) {
            log.error("❌ Ошибка при показе подписок: chatId={}", chatId, e);
            message.setText("❌ Произошла ошибка при загрузке подписок. Попробуйте позже.");
        }
    }

    @PostConstruct
    public void initCommands() {
        List<BotCommand> commandList = List.of(
                new BotCommand("/start", "🚀 Запустить бота")
        );

        try {
            execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
            log.info("✅ Команды бота успешно установлены.");
        } catch (TelegramApiException e) {
            log.error("⚠️ Ошибка установки команд бота:", e);
        }
    }

    private boolean registerChatId(String userId, String chatId) {
        log.info("📝 Регистрация chatId: userId={}, chatId={}", userId, chatId);

        try {
            Long user = Long.parseLong(userId);
            Long chat = Long.parseLong(chatId);

            if (userService.hasChatId(chat)) {
                log.info("ℹ️ ChatId уже зарегистрирован: chatId={}", chatId);
                return false; // Уже зарегистрирован
            }

            userService.registerChatId(user, chat);
            log.info("✅ ChatId успешно зарегистрирован: userId={}, chatId={}", userId, chatId);
            return true;
        } catch (Exception e) {
            log.error("❌ Ошибка при сохранении chatId: userId={}, chatId={}", userId, chatId, e);
            return false;
        }
    }

    private void subscribeToType(String chatId, String subscriptionType) {
        log.info("➕ Попытка подписки: chatId={}, type={}", chatId, subscriptionType);

        try {
            NotificationEnum notificationEnum = NotificationEnum.valueOf(subscriptionType.toUpperCase());
            log.info("🔄 Вызываем telegramUserService.subcribe: enum={}, chatId={}", notificationEnum, chatId);

            telegramUserService.subscribe(notificationEnum, Long.parseLong(chatId));

            log.info("✅ Подписка успешно выполнена: chatId={}, type={}", chatId, subscriptionType);
        } catch (IllegalArgumentException e) {
            log.error("❌ Неверный тип подписки: chatId={}, type={}", chatId, subscriptionType, e);
        } catch (Exception e) {
            log.error("❌ Ошибка при подписке на {}: chatId={}", subscriptionType, chatId, e);
        }
    }

    private void unsubscribeFromType(String chatId, String subscriptionType) {
        log.info("➖ Попытка отписки: chatId={}, type={}", chatId, subscriptionType);

        try {
            NotificationEnum notificationType = NotificationEnum.valueOf(subscriptionType.toUpperCase());
            log.info("🔄 Вызываем telegramUserService.unsubscribe: enum={}, chatId={}", notificationType, chatId);

            telegramUserService.unsubscribe(notificationType, Long.parseLong(chatId));

            log.info("✅ Отписка успешно выполнена: chatId={}, type={}", chatId, subscriptionType);
        } catch (IllegalArgumentException e) {
            log.error("❌ Неверный тип отписки: chatId={}, type={}", chatId, subscriptionType, e);
        } catch (Exception e) {
            log.error("❌ Ошибка при отписке от {}: chatId={}", subscriptionType, chatId, e);
        }
    }

    private void subscribeToAll(String chatId) {
        log.info("➕ Попытка подписки на все: chatId={}", chatId);

        try {
            log.info("🔄 Вызываем userService.subscribe: chatId={}", chatId);
            telegramUserService.subscribeToAll(Long.parseLong(chatId));
            log.info("✅ Подписка на все успешно выполнена: chatId={}", chatId);
        } catch (Exception e) {
            log.error("❌ Ошибка при подписке на все: chatId={}", chatId, e);
        }
    }

    private void unsubscribeFromAll(String chatId) {
        log.info("➖ Попытка отписки от всего: chatId={}", chatId);

        try {
            log.info("🔄 Вызываем userService.unsubscribe: chatId={}", chatId);
            telegramUserService.unsubscribeFromAll(Long.parseLong(chatId));
            log.info("✅ Отписка от всего успешно выполнена: chatId={}", chatId);
        } catch (Exception e) {
            log.error("❌ Ошибка при отписке от всех: chatId={}", chatId, e);
        }
    }

    private List<String> getUserSubscriptions(String chatId) {
        try {
            return telegramUserService.getAllSubscribedUsersType(Long.parseLong(chatId));
        } catch (Exception e) {
            log.warn("Ошибка при получении подписок: chatId={}", chatId, e);
            return new ArrayList<>();
        }
    }










    // Обновленные методы для класса TelegramBot

    private void handleSubscriptionCallback(String callbackData, String chatId, SendMessage message) {
        String subscriptionType = callbackData.replace("sub_", "");
        log.info("Обработка подписки: type={}, chatId={}", subscriptionType, chatId);

        if ("all".equals(subscriptionType)) {
            int newSubscriptions = telegramUserService.subscribeToAll(Long.parseLong(chatId));
            if (newSubscriptions > 0) {
                message.setText(createSubscribeToAllSuccessText(newSubscriptions));
            } else {
                message.setText(createAlreadySubscribedToAllText());
            }
        } else {
            try {
                NotificationEnum notificationEnum = NotificationEnum.valueOf(subscriptionType.toUpperCase());
                SubscriptionResult result =
                        telegramUserService.subscribe(notificationEnum, Long.parseLong(chatId));

                String typeName = getSubscriptionName(subscriptionType);

                switch (result) {
                    case SUCCESS -> message.setText(createSubscriptionSuccessText(typeName));
                    case ALREADY_SUBSCRIBED -> message.setText(createAlreadySubscribedText(typeName));
                    case ERROR -> message.setText(createSubscriptionErrorText(typeName));
                }
            } catch (IllegalArgumentException e) {
                log.error("Неверный тип подписки: {}", subscriptionType, e);
                message.setText(createInvalidSubscriptionTypeText());
            }
        }
        message.setReplyMarkup(subscriptionManagementKeyboard());
    }

    private void handleUnsubscriptionCallback(String callbackData, String chatId, SendMessage message) {
        String subscriptionType = callbackData.replace("unsub_", "");
        log.info("Обработка отписки: type={}, chatId={}", subscriptionType, chatId);

        if ("all".equals(subscriptionType)) {
            telegramUserService.unsubscribeFromAll(Long.parseLong(chatId));
            message.setText(createUnsubscribeFromAllSuccessText());
        } else {
            try {
                NotificationEnum notificationEnum = NotificationEnum.valueOf(subscriptionType.toUpperCase());
                UnsubscriptionResult result =
                        telegramUserService.unsubscribe(notificationEnum, Long.parseLong(chatId));

                String typeName = getSubscriptionName(subscriptionType);

                switch (result) {
                    case SUCCESS -> message.setText(createUnsubscriptionSuccessText(typeName));
                    case NOT_SUBSCRIBED -> message.setText(createNotSubscribedText(typeName));
                    case ERROR -> message.setText(createUnsubscriptionErrorText(typeName));
                }
            } catch (IllegalArgumentException e) {
                log.error("Неверный тип отписки: {}", subscriptionType, e);
                message.setText(createInvalidSubscriptionTypeText());
            }
        }

        showMySubscriptions(chatId, message);
    }

    private void handleQuickUnsubscriptionCallback(String callbackData, String chatId, SendMessage message) {
        String subscriptionType = callbackData.replace("quick_unsub_", "");
        log.info("Быстрая отписка: type={}, chatId={}", subscriptionType, chatId);

        try {
            NotificationEnum notificationEnum = NotificationEnum.valueOf(subscriptionType.toUpperCase());
            UnsubscriptionResult result =
                    telegramUserService.unsubscribe(notificationEnum, Long.parseLong(chatId));

            String typeName = getSubscriptionName(subscriptionType);

            switch (result) {
                case SUCCESS -> message.setText(createQuickUnsubscriptionSuccessText(typeName));
                case NOT_SUBSCRIBED -> message.setText(createQuickNotSubscribedText(typeName));
                case ERROR -> message.setText(createQuickUnsubscriptionErrorText(typeName));
            }
        } catch (IllegalArgumentException e) {
            log.error("Неверный тип быстрой отписки: {}", subscriptionType, e);
            message.setText(createInvalidSubscriptionTypeText());
        }

        showQuickUnsubscribe(chatId, message);
    }

// Новые методы для создания текстов сообщений

    private String createSubscribeToAllSuccessText(int newSubscriptions) {
        return String.format("""
            ✅ <b>Массовая подписка завершена!</b>
            
            🎯 <b>Результат:</b>
            • Новых подписок: <code>%d</code>
            • Всего активных: <code>5</code>
            
            📬 Теперь вы получаете все типы уведомлений!
            
            💡 <i>Используйте 'Мои подписки' для просмотра</i>
            """, newSubscriptions);
    }

    private String createAlreadySubscribedToAllText() {
        return """
            ℹ️ <b>Уже подписаны на все!</b>
            
            ✅ <b>Статус подписок:</b>
            У вас уже активны все типы уведомлений
            
            🎯 <b>Доступные действия:</b>
            • Просмотр подписок
            • Выборочная отписка
            • Быстрая отписка
            
            💡 <i>Все уведомления уже настроены оптимально</i>
            """;
    }

    private String createUnsubscribeFromAllSuccessText() {
        return """
            ✅ <b>Отписка от всех выполнена!</b>
            
            🔕 <b>Результат:</b>
            • Отключены ВСЕ уведомления
            • Активных подписок: <code>0</code>
            
            💡 <b>Восстановление:</b>
            Подписки можно восстановить через 'Управление подписками'
            
            ℹ️ <i>Изменения вступили в силу немедленно</i>
            """;
    }

    private String createAlreadySubscribedText(String subscriptionName) {
        return String.format("""
            ℹ️ <b>Уже подписаны!</b>
            
            ✅ <b>Статус:</b> <code>%s</code>
            У вас уже есть активная подписка на этот тип уведомлений
            
            🔔 <b>Уведомления поступают в полном объеме</b>
            
            💡 <b>Доступные действия:</b>
            • Отписаться от этого типа
            • Настроить другие типы
            • Просмотреть все подписки
            
            ℹ️ <i>Повторная подписка не требуется</i>
            """, subscriptionName);
    }

    private String createNotSubscribedText(String subscriptionName) {
        return String.format("""
            ℹ️ <b>Не подписаны!</b>
            
            ❌ <b>Статус:</b> <code>%s</code>
            У вас нет активной подписки на этот тип уведомлений
            
            💡 <b>Для активации:</b>
            Используйте 'Управление подписками' → выберите нужный тип
            
            🔔 <b>Доступно для подписки:</b>
            • Олимпиады
            • Новости
            • Публикации
            • Блоги
            • Мероприятия
            
            ℹ️ <i>Отписка от неактивной подписки не выполнена</i>
            """, subscriptionName);
    }

    private String createQuickNotSubscribedText(String subscriptionName) {
        return String.format("""
            ℹ️ <b>Быстрая отписка: не выполнена</b>
            
            ❌ <b>Причина:</b>
            Вы не подписаны на: <code>%s</code>
            
            🔍 <b>Проверьте:</b>
            • Список активных подписок
            • Возможно, уже отписались ранее
            
            💡 <b>Рекомендации:</b>
            Обновите список подписок для актуальной информации
            
            ⚡ <i>Быстрая отписка работает только с активными подписками</i>
            """, subscriptionName);
    }

    private String createUnsubscriptionSuccessText(String subscriptionName) {
        return String.format("""
            ✅ <b>Отписка выполнена!</b>
            
            ❌ <b>Отключено:</b> <code>%s</code>
            
            🔕 <b>Изменения:</b>
            • Уведомления этого типа прекращены
            • Подписка удалена из системы
            • Изменения действуют немедленно
            
            🔄 <b>Восстановление:</b>
            Подписку можно восстановить через 'Управление подписками'
            
            💡 <i>Другие активные подписки не затронуты</i>
            """, subscriptionName);
    }

    private String createSubscriptionErrorText(String subscriptionName) {
        return String.format("""
            ❌ <b>Ошибка подписки!</b>
            
            🚨 <b>Не удалось подписаться на:</b> <code>%s</code>
            
            🔧 <b>Возможные причины:</b>
            • Временная недоступность системы
            • Проблемы с подключением
            • Техническая ошибка сервера
            
            💡 <b>Рекомендации:</b>
            • Попробуйте через несколько минут
            • Проверьте подключение
            • Обратитесь в поддержку при повторении
            
            🔄 <i>Повторите попытку позже</i>
            """, subscriptionName);
    }

    private String createUnsubscriptionErrorText(String subscriptionName) {
        return String.format("""
            ❌ <b>Ошибка отписки!</b>
            
            🚨 <b>Не удалось отписаться от:</b> <code>%s</code>
            
            🔧 <b>Возможные причины:</b>
            • Временная недоступность системы
            • Проблемы с подключением к базе данных
            • Техническая ошибка сервера
            
            💡 <b>Рекомендации:</b>
            • Попробуйте через несколько минут
            • Используйте альтернативные способы отписки
            • Обратитесь в поддержку при повторении
            
            🔄 <i>Повторите попытку позже</i>
            """, subscriptionName);
    }

    private String createQuickUnsubscriptionErrorText(String subscriptionName) {
        return String.format("""
            ❌ <b>Быстрая отписка: ошибка!</b>
            
            🚨 <b>Не удалось отписаться от:</b> <code>%s</code>
            
            ⚡ <b>Проблема с быстрой отпиской:</b>
            • Системная ошибка
            • Временная недоступность
            
            🔧 <b>Альтернативы:</b>
            • Используйте обычную отписку через 'Мои подписки'
            • Попробуйте позже
            • Отписка от всех сразу
            
            🔄 <i>Быстрая отписка временно недоступна</i>
            """, subscriptionName);
    }

    private String createInvalidSubscriptionTypeText() {
        return """
            ❌ <b>Неизвестный тип подписки!</b>
            
            🚨 <b>Ошибка системы:</b>
            Указан несуществующий тип уведомлений
            
            ✅ <b>Доступные типы:</b>
            • 🏆 Олимпиады
            • 📰 Новости
            • 📝 Публикации
            • 📖 Блоги
            • 🎉 Мероприятия
            
            🔄 <b>Решение:</b>
            • Обновите бота
            • Используйте кнопки меню
            • Перезапустите /start
            
            💡 <i>Выбирайте только из предложенных вариантов</i>
            """;
    }
}
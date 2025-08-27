package kg.edu.mathbilim.telegram.service;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.telegram.bot.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramNotificationService {

    @Lazy
    private final TelegramBot telegramBot;
    private final UserService userService;
    private final FileService fileService;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private static final Map<NotificationEnum, String> NOTIFICATION_TYPES = new HashMap<>();
    private static final Map<NotificationEnum, String> NOTIFICATION_URLS = new HashMap<>();

    static {
        NOTIFICATION_TYPES.put(NotificationEnum.NEWS, "📰 Новая новость!");
        NOTIFICATION_TYPES.put(NotificationEnum.POST, "📢 Новый пост!");
        NOTIFICATION_TYPES.put(NotificationEnum.BLOG, "📝 Новый блог!");
        NOTIFICATION_TYPES.put(NotificationEnum.EVENT, "🎯 Новое событие!");
        NOTIFICATION_TYPES.put(NotificationEnum.OLYMPIAD, "🏆 Новая олимпиада!");

        NOTIFICATION_URLS.put(NotificationEnum.NEWS, "/news/");
        NOTIFICATION_URLS.put(NotificationEnum.POST, "/posts/");
        NOTIFICATION_URLS.put(NotificationEnum.BLOG, "/blog/");
        NOTIFICATION_URLS.put(NotificationEnum.EVENT, "/events/");
        NOTIFICATION_URLS.put(NotificationEnum.OLYMPIAD, "/olympiad/");
    }

    public void sendNotificationToUser(Long telegramId, NotificationEnum type, NotificationData data) {
        log.info("Отправка уведомления пользователю {} типа {}", telegramId, type);

        try {
            if (data.getMainImageId() != null && data.getMainImageId() > 0) {
                sendNotificationWithPhoto(telegramId, type, data);
            } else {
                sendSimpleTextMessage(telegramId, type, data);
            }
            log.info("Уведомление успешно отправлено пользователю {}", telegramId);
        } catch (Exception e) {
            log.error("Ошибка отправки уведомления пользователю {}: {}", telegramId, e.getMessage());
            sendFallbackMessage(telegramId, data);
        }
    }

    private void sendNotificationWithPhoto(Long telegramId, NotificationEnum type, NotificationData data) throws TelegramApiException {
        try {
            if (!fileService.existsById(data.getMainImageId())) {
                log.warn("Файл с ID {} не найден, отправляем текстовое сообщение", data.getMainImageId());
                sendSimpleTextMessage(telegramId, type, data);
                return;
            }

            var fileDto = fileService.getById(data.getMainImageId());
            if (fileDto == null || !isImageFile(fileDto.getType().getMimeType())) {
                log.warn("Файл с ID {} не является изображением, отправляем текстовое сообщение", data.getMainImageId());
                sendSimpleTextMessage(telegramId, type, data);
                return;
            }

            try {
                sendPhotoByUrl(telegramId, type, data);
            } catch (TelegramApiException urlException) {
                log.warn("Не удалось отправить фото через URL, пробуем через байты");
                sendPhotoByBytes(telegramId, type, data);
            }

        } catch (Exception e) {
            log.error("Ошибка при отправке фото: {}", e.getMessage());
            sendSimpleTextMessage(telegramId, type, data);
        }
    }

    private void sendPhotoByUrl(Long telegramId, NotificationEnum type, NotificationData data) throws TelegramApiException {
        String photoUrl = baseUrl + "/api/files/" + data.getMainImageId() + "/view";

        SendPhoto photo = new SendPhoto();
        photo.setChatId(telegramId.toString());
        photo.setPhoto(new InputFile(photoUrl));
        photo.setParseMode("HTML");

        String caption = formatNotificationText(type, data);
        if (caption != null && !caption.trim().isEmpty()) {
            if (caption.length() > 1020) {
                caption = caption.substring(0, 1020) + "...";
            }
            photo.setCaption(caption);
        }

        telegramBot.execute(photo);
    }

    private void sendPhotoByBytes(Long telegramId, NotificationEnum type, NotificationData data) throws TelegramApiException {
        try {
            byte[] fileBytes = fileService.downloadFile(data.getMainImageId());
            if (fileBytes == null || fileBytes.length == 0) {
                throw new TelegramApiException("Empty file bytes");
            }

            var fileDto = fileService.getById(data.getMainImageId());

            SendPhoto photo = new SendPhoto();
            photo.setChatId(telegramId.toString());
            photo.setParseMode("HTML");

            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            photo.setPhoto(new InputFile(inputStream, fileDto.getFilename()));

            String caption = formatNotificationText(type, data);
            if (caption != null && !caption.trim().isEmpty()) {
                if (caption.length() > 1020) {
                    caption = caption.substring(0, 1020) + "...";
                }
                photo.setCaption(caption);
            }

            telegramBot.execute(photo);

        } catch (Exception e) {
            throw new TelegramApiException("Failed to send photo by bytes", e);
        }
    }

    private void sendSimpleTextMessage(Long telegramId, NotificationEnum type, NotificationData data) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(telegramId.toString());
        message.setParseMode("HTML");

        String text = formatNotificationText(type, data);
        if (text == null || text.trim().isEmpty()) {
            text = "Новое уведомление";
        }

        if (text.length() > 4096) {
            text = text.substring(0, 4090) + "...";
        }

        message.setText(text);
        telegramBot.execute(message);
    }

    private String formatNotificationText(NotificationEnum type, NotificationData data) {
        if (data == null) {
            log.warn("NotificationData is null");
            return "Новое уведомление";
        }

        try {
            log.info("=== ОТЛАДКА ФОРМИРОВАНИЯ ССЫЛКИ ===");
            log.info("Type: {}", type);
            log.info("Data ID: {}", data.getId());
            log.info("Base URL: {}", baseUrl);

            StringBuilder message = new StringBuilder();

            // Заголовок типа уведомления
            String contentType = NOTIFICATION_TYPES.getOrDefault(type, "🔔 Новое уведомление!");
            message.append("<b>").append(escapeHtml(contentType)).append("</b>\n\n");

            // Заголовок
            if (data.getTitle() != null && !data.getTitle().trim().isEmpty()) {
                String title = cleanText(data.getTitle().trim());
                if (title.length() > 80) {
                    title = title.substring(0, 77) + "...";
                }
                message.append("<b>📋 Заголовок:</b> ").append(escapeHtml(title)).append("\n\n");
            }

            // Описание
            if (data.getDescription() != null && !data.getDescription().trim().isEmpty()) {
                String desc = cleanText(data.getDescription().trim());
                if (desc.length() > 200) {
                    desc = desc.substring(0, 197) + "...";
                }
                message.append("<b>📝 Описание:</b> ").append(escapeHtml(desc)).append("\n\n");
            }

            // Ссылка - с подробным логированием
            if (data.getId() != null) {
                log.info("Data ID не null: {}", data.getId());
                String urlPath = NOTIFICATION_URLS.get(type);
                log.info("URL Path для типа {}: {}", type, urlPath);

                if (urlPath != null) {
                    String fullUrl = baseUrl + urlPath + data.getId();
                    log.info("Сформированная ссылка: {}", fullUrl);
                    message.append("<a href=\"").append(fullUrl).append("\">👉 Читать полностью</a>");
                    log.info("Ссылка добавлена в сообщение");
                } else {
                    log.error("URL Path is null для типа: {}", type);
                }
            } else {
                log.warn("Data ID is null");
            }

            String result = message.toString();
            log.info("Итоговое сообщение: {}", result);
            log.info("=== КОНЕЦ ОТЛАДКИ ===");
            return result;

        } catch (Exception e) {
            log.error("Ошибка форматирования текста: {}", e.getMessage(), e);
            return formatSimpleFallback(type, data);
        }
    }

    private String formatSimpleFallback(NotificationEnum type, NotificationData data) {
        try {
            StringBuilder message = new StringBuilder();

            String contentType = NOTIFICATION_TYPES.getOrDefault(type, "🔔 Новое уведомление!");
            message.append(contentType.replaceAll("[<>]", "")).append("\n\n");

            if (data != null && data.getTitle() != null && !data.getTitle().trim().isEmpty()) {
                String title = cleanText(data.getTitle().trim());
                if (title.length() > 100) {
                    title = title.substring(0, 97) + "...";
                }
                message.append("📋 ").append(title).append("\n\n");
            }

            if (data != null && data.getId() != null) {
                String urlPath = NOTIFICATION_URLS.get(type);
                if (urlPath != null) {
                    String fullUrl = baseUrl + urlPath + data.getId();
                    message.append("👉 Читать: ").append(fullUrl);
                }
            }

            return message.toString();
        } catch (Exception e) {
            log.error("Критическая ошибка форматирования: {}", e.getMessage());
            return "🔔 Новое уведомление на сайте!";
        }
    }

    private String cleanText(String text) {
        if (text == null) {
            return "";
        }

        try {
            return text
                    .replaceAll("<[^>]+>", "")           // Удаляем HTML теги
                    .replaceAll("&nbsp;", " ")
                    .replaceAll("&amp;", "&")
                    .replaceAll("&lt;", "<")
                    .replaceAll("&gt;", ">")
                    .replaceAll("&quot;", "\"")
                    .replaceAll("&#\\d+;", "")          // Удаляем HTML entities
                    .replaceAll("\\s+", " ")            // Убираем лишние пробелы
                    .trim();
        } catch (Exception e) {
            log.error("Ошибка очистки текста: {}", e.getMessage());
            return text.length() > 50 ? text.substring(0, 50) + "..." : text;
        }
    }

    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    private void sendFallbackMessage(Long telegramId, NotificationData data) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(telegramId.toString());

            StringBuilder fallbackText = new StringBuilder();
            fallbackText.append("🔔 Новое уведомление\n\n");

            if (data != null && data.getTitle() != null && !data.getTitle().trim().isEmpty()) {
                String title = cleanText(data.getTitle().trim());
                if (title.length() > 100) {
                    title = title.substring(0, 97) + "...";
                }
                fallbackText.append(title).append("\n\n");
            }

            if (data != null && data.getId() != null) {
                fallbackText.append("Проверьте новости на сайте!");
            }

            message.setText(fallbackText.toString());
            telegramBot.execute(message);

        } catch (Exception e) {
            log.error("Критическая ошибка отправки резервного сообщения: {}", e.getMessage());
        }
    }

    private boolean isImageFile(String mimeType) {
        if (mimeType == null) {
            return false;
        }

        String normalizedMimeType = mimeType.toLowerCase();
        return normalizedMimeType.startsWith("image/") &&
                (normalizedMimeType.contains("jpeg") || normalizedMimeType.contains("jpg") ||
                        normalizedMimeType.contains("png") || normalizedMimeType.contains("gif") ||
                        normalizedMimeType.contains("webp") || normalizedMimeType.contains("bmp"));
    }

    // ============ ОСНОВНЫЕ МЕТОДЫ ДЛЯ РАБОТЫ С ПОДПИСЧИКАМИ ============

    public void sendVisitNotification(Long chatId) {
        SendAnimation animation = new SendAnimation();
        animation.setChatId(chatId.toString());
        animation.setAnimation(new InputFile("https://media.giphy.com/media/3o7TKtd1LXe5C6TgYM/giphy.gif"));
        animation.setCaption("⚡ Кто-то только что зашёл на сайт!");

        try {
            telegramBot.execute(animation);
            log.info("GIF-уведомление отправлено пользователю {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки GIF-уведомления пользователю {}: {}", chatId, e.getMessage());
        }
    }

    public void sendPhotoFromDatabase(Long chatId, Long fileId, String caption) {
        log.info("Отправка фото из БД пользователю {}: fileId={}", chatId, fileId);

        try {
            if (!fileService.existsById(fileId)) {
                log.error("Файл с ID {} не найден в БД", fileId);
                return;
            }

            FileDto fileDto = fileService.getById(fileId);
            if (!isImageFile(fileDto.getType().getMimeType())) {
                log.error("Файл с ID {} не является изображением", fileId);
                return;
            }

            try {
                sendPhotoFromDatabaseByUrl(chatId, fileId, caption);
            } catch (TelegramApiException e) {
                sendPhotoFromDatabaseByBytes(chatId, fileId, caption, fileDto);
            }

        } catch (Exception e) {
            log.error("Ошибка отправки фото из БД пользователю {}: {}", chatId, e.getMessage());
        }
    }

    private void sendPhotoFromDatabaseByUrl(Long chatId, Long fileId, String caption) throws TelegramApiException {
        String photoUrl = baseUrl + "/api/files/" + fileId + "/view";

        SendPhoto photo = new SendPhoto();
        photo.setChatId(chatId.toString());
        photo.setPhoto(new InputFile(photoUrl));

        if (caption != null && !caption.trim().isEmpty()) {
            String safeCaption = caption.length() > 1020 ? caption.substring(0, 1020) + "..." : caption;
            photo.setCaption(safeCaption);
        }

        telegramBot.execute(photo);
    }

    private void sendPhotoFromDatabaseByBytes(Long chatId, Long fileId, String caption, FileDto fileDto) {
        try {
            byte[] fileBytes = fileService.downloadFile(fileId);
            if (fileBytes == null || fileBytes.length == 0) {
                return;
            }

            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId.toString());

            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            photo.setPhoto(new InputFile(inputStream, fileDto.getFilename()));

            if (caption != null && !caption.trim().isEmpty()) {
                String safeCaption = caption.length() > 1020 ? caption.substring(0, 1020) + "..." : caption;
                photo.setCaption(safeCaption);
            }

            telegramBot.execute(photo);

        } catch (Exception e) {
            log.error("Ошибка отправки фото через байты: {}", e.getMessage());
        }
    }

    public String getFileViewUrl(Long fileId) {
        return baseUrl + "/api/files/" + fileId + "/view";
    }
}
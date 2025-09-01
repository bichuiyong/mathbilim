package kg.edu.mathbilim.dto.event;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.util.TranslationUtil;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import kg.edu.mathbilim.validation.annotation.ValidDateTimeRange;
import kg.edu.mathbilim.validation.annotation.ValidEventLocation;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ValidDateTimeRange(
        startDateTimeField = "startDate",
        endDateTimeField = "endDate"
)
@ValidEventLocation
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto extends ContentDto {
    @NotNull(message = "Дата начала мероприятия обязательна")
    @Future(message = "Дата начала мероприятия должна быть в будущем")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime startDate;

    @Future(message = "Дата окончания должна быть в будущем")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime endDate;

    @NotNull(message = "Необходимо выбрать тип мероприятия")
    Long typeId;

    String address; // если оффлайн

    String url; // если онлайн

    @NotNull(message = "Необходимо указать тип мероприятия (онлайн/офлайн)")
    Boolean isOffline;

    @Builder.Default
    List<FileDto> eventFiles = new ArrayList<>();

    @AtLeastOneTranslationRequired
    @Builder.Default
    List<@Valid EventTranslationDto> eventTranslations = createDefaultTranslations();

    static List<EventTranslationDto> createDefaultTranslations() {
        return TranslationUtil.createDefaultTranslations(languageCode ->
                EventTranslationDto.builder()
                        .languageCode(languageCode)
                        .build()
        );
    }
}
package kg.edu.mathbilim.dto.event;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.util.TranslationUtil;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import kg.edu.mathbilim.validation.annotation.ValidDateTimeRange;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto extends ContentDto {
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime startDate;

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime endDate;

    @NotNull
    Long typeId;

    String address; // если оффлайн

    String url; // если онлайн

    @NotNull
    Boolean isOffline;

    @Builder.Default
    List<FileDto> eventFiles = new ArrayList<>();

    @AtLeastOneTranslationRequired
    @Builder.Default
    List<EventTranslationDto> eventTranslations = createDefaultTranslations();

    static List<EventTranslationDto> createDefaultTranslations() {
        return TranslationUtil.createDefaultTranslations(languageCode ->
                EventTranslationDto.builder()
                        .languageCode(languageCode)
                        .build()
        );
    }
}
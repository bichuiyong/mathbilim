package kg.edu.mathbilim.dto.event;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import kg.edu.mathbilim.validation.annotation.ValidDateTimeRange;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ValidDateTimeRange(
        startDateTimeField = "startDate",
        endDateTimeField = "endDate"
)
public class EventDto extends ContentDto {
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;

    @NotNull
    private Long typeId;

    private String address; // если оффлайн

    private String url; // если онлайн

    @NotNull
    private Boolean isOffline;

    @Builder.Default
    private List<FileDto> eventFiles = new ArrayList<>();

    @AtLeastOneTranslationRequired
    @Builder.Default
    private List<EventTranslationDto> eventTranslations = createDefaultTranslations();

    private static List<EventTranslationDto> createDefaultTranslations() {
        return Arrays.stream(Language.values())
                .map(lang -> EventTranslationDto.builder()
                        .languageCode(lang.getCode())
                        .title("")
                        .content("")
                        .build())
                .collect(Collectors.toList());
    }
}
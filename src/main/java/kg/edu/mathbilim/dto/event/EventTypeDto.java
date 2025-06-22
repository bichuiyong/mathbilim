package kg.edu.mathbilim.dto.event;

import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventTypeDto {
    Integer id;

    @AllTranslationsRequired
    List<EventTypeTranslationDto> eventTypeTranslations;
}

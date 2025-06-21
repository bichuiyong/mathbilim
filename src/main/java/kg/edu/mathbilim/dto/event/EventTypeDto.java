package kg.edu.mathbilim.dto.event;

import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventTypeDto {
    private Integer id;

    @AllTranslationsRequired
    private List<EventTypeTranslationDto> eventTypeTranslations;
}

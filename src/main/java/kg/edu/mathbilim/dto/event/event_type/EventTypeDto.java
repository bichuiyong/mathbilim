package kg.edu.mathbilim.dto.event.event_type;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventTypeDto {
    private Integer id;

    private List<EventTypeTranslationDto> eventTypeTranslations;
}

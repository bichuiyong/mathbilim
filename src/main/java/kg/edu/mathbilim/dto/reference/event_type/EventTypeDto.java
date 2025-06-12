package kg.edu.mathbilim.dto.reference.event_type;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventTypeDto {
    private Integer id;

    private Set<EventTypeTranslationDto> eventTypeTranslations;
}

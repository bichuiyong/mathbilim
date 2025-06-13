package kg.edu.mathbilim.dto.event.event_type;

import kg.edu.mathbilim.dto.translations.TypeTranslationDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventTypeTranslationDto extends TypeTranslationDto {
    private Integer eventTypeId;
}
package kg.edu.mathbilim.dto.event;

import kg.edu.mathbilim.dto.translations.ContentTranslationDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventTranslationDto extends ContentTranslationDto {

    Long eventId;
}
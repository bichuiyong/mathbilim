package kg.edu.mathbilim.dto.event;

import kg.edu.mathbilim.dto.abstracts.TypeTranslationDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class EventTypeTranslationDto extends TypeTranslationDto {
    public EventTypeTranslationDto() {
        super();
    }
}
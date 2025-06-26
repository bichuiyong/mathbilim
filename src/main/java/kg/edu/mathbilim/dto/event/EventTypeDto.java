package kg.edu.mathbilim.dto.event;

import kg.edu.mathbilim.dto.abstracts.BaseTypeDto;
import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventTypeDto extends BaseTypeDto<EventTypeTranslationDto> {

}

package kg.edu.mathbilim.dto.abstracts;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ContentTranslationDto {
    String languageCode;

    String title;

    String content;
}

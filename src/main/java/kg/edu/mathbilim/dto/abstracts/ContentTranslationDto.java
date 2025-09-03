package kg.edu.mathbilim.dto.abstracts;

import jakarta.validation.constraints.Size;
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

    @Size(max = 255, message = "Название не должно превышать 255 символов")
    String title;

    String content;
}

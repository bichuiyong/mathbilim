package kg.edu.mathbilim.dto.abstracts;

import jakarta.validation.constraints.NotBlank;
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
public abstract class TypeTranslationDto {

    Integer typeId;
    @NotBlank
    @Size(min = 2, max = 2, message = "Language code must be exactly 2 characters")
    String languageCode;

    @NotBlank
    @Size(max = 100, message = "Translation must not exceed 100 characters")
    String translation;
}

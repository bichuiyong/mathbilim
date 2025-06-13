package kg.edu.mathbilim.dto.translations;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class TypeTranslationDto {
    @NotBlank
    private String languageCode;

    @NotBlank
    private String translation;
}

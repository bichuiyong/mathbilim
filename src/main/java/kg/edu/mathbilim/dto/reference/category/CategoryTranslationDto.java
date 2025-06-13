package kg.edu.mathbilim.dto.reference.category;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryTranslationDto {

    Integer categoryId;

    @NotBlank
    private String languageCode;

    @NotBlank
    private String translation;
}
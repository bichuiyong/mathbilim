package kg.edu.mathbilim.dto.reference;

import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;

    @AllTranslationsRequired
    private List<CategoryTranslationDto> categoryTranslations;
}

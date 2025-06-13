package kg.edu.mathbilim.dto.reference.category;

import kg.edu.mathbilim.dto.translations.TypeTranslationDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryTranslationDto extends TypeTranslationDto {
    Integer categoryId;
}
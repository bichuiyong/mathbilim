package kg.edu.mathbilim.dto.reference;

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
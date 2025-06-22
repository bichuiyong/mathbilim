package kg.edu.mathbilim.dto.reference;

import kg.edu.mathbilim.dto.abstracts.TypeTranslationDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryTranslationDto extends TypeTranslationDto {
    Integer categoryId;
}
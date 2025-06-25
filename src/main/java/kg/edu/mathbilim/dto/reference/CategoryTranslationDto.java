package kg.edu.mathbilim.dto.reference;

import jdk.jfr.Category;
import kg.edu.mathbilim.dto.abstracts.TypeTranslationDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class CategoryTranslationDto extends TypeTranslationDto {
    public CategoryTranslationDto() {
        super();
    }
}
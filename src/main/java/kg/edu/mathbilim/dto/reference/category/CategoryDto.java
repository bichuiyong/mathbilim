package kg.edu.mathbilim.dto.reference.category;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;

    private Set<CategoryTranslationDto> categoryTranslations;
}

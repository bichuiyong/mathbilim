package kg.edu.mathbilim.dto.reference.category;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;

    private List<CategoryTranslationDto> categoryTranslations;
}

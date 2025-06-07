package kg.edu.mathbilim.dto.reference;

import jakarta.validation.constraints.NotBlank;
import kg.edu.mathbilim.validation.annotation.UniqueCategory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;

    @UniqueCategory
    @NotBlank
    private String name;

    private CategoryDto parent;
}

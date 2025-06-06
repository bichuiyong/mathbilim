package kg.edu.mathbilim.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;

    private String name;

    private CategoryDto parent;
}

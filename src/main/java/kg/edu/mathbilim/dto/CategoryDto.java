package kg.edu.mathbilim.dto;


import kg.edu.mathbilim.validation.annotation.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;

    @Category
    private String name;
}

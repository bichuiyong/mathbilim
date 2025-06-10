package kg.edu.mathbilim.dto;


import kg.edu.mathbilim.validation.annotation.PostType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTypeDto {
    private Integer id;
    @PostType
    private String name;
}

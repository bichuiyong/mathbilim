package kg.edu.mathbilim.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostTypeDto {
    private Integer id;

    private String name;

    private PostTypeDto parent;
}
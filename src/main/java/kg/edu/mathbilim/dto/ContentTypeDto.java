package kg.edu.mathbilim.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentTypeDto {
    private Integer id;

    private String name;

    private ContentTypeDto parent;
}

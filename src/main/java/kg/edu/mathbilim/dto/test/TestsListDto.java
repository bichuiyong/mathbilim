package kg.edu.mathbilim.dto.test;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestsListDto {
    private Long id;
    private String name;
    private String description;
    private Boolean hasLimit;
    private Integer timeLimit;
    private Integer questionCount;
}

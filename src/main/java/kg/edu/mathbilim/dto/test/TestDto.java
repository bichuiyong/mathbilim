package kg.edu.mathbilim.dto.test;

import kg.edu.mathbilim.model.File;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private Long id;
    private String name;
    private File file;
    private Integer timeLimit;
    private Integer questionCount;
}

package kg.edu.mathbilim.dto.test;

import kg.edu.mathbilim.model.File;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private Long id;
    private String name;
    private String description;
    private File file;
    private Boolean hasLimit;
    private Integer timeLimit;
    private Integer questionCount;

    private List<QuestionDto> questionDtoList;
}

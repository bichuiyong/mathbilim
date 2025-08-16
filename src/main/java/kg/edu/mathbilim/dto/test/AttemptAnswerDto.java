package kg.edu.mathbilim.dto.test;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttemptAnswerDto {
    private Integer questionNumber;
    private String chosenAnswer;
}

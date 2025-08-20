package kg.edu.mathbilim.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestPassDto {
    private List<AttemptAnswerDto> attemptAnswerDtoList;
}

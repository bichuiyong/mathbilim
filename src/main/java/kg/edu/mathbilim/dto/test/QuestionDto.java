package kg.edu.mathbilim.dto.test;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    @NotNull
    private Integer numberOrder;
    @NotNull
    private Integer testPageNumber;
    private boolean textFormat;
    @NotBlank
    private String correctAnswer;
    @NotNull
    private Double weight;
    @NotNull
    private Long topicId;
}

package kg.edu.mathbilim.dto.test;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestChoiceDto {
    private TestDto test;

    private Integer questionNumber;

    private String questionValue;

    private Instant answeredAt;
}
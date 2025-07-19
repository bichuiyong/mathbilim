package kg.edu.mathbilim.dto.test;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestChoiceDto {
    TestDto test;

    Integer questionNumber;

    String questionValue;

    LocalDateTime answeredAt;
}
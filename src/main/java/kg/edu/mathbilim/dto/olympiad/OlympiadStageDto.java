package kg.edu.mathbilim.dto.olympiad;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympiadStageDto {
    Long id;

    Integer stageOrder;

    LocalDate registrationStart;

    LocalDate registrationEnd;

    LocalDate startDate;

    LocalDate endDate;

    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt = LocalDateTime.now();
}

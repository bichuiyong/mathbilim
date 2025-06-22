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

    OlympiadDto olympiad;

    String name;

    Integer stageOrder;

    String description;

    LocalDate registrationStart;

    LocalDate registrationEnd;

    LocalDate eventStartDate;

    LocalDate eventEndDate;

    String location;

    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt = LocalDateTime.now();
}

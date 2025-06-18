package kg.edu.mathbilim.dto.olympiad;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class OlympiadStageDto {
    private Long id;

    private OlympiadDto olympiad;

    private String name;

    private Integer stageOrder;

    private String description;

    private LocalDate registrationStart;

    private LocalDate registrationEnd;

    private LocalDate eventStartDate;

    private LocalDate eventEndDate;

    private String location;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}

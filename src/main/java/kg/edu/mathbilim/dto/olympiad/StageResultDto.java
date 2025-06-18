package kg.edu.mathbilim.dto.olympiad;

import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StageResultDto {
    private Long id;

    private UserDto user;

    private OlympiadStage stage;

    private Integer score;

    private Integer maxScore;

    private Double percentage;

    private Boolean isQualified;

    private Integer rankPosition;

    private String participantNumber;

    private String notes;

    private LocalDate resultDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

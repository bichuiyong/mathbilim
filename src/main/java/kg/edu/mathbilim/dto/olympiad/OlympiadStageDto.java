package kg.edu.mathbilim.dto.olympiad;

import kg.edu.mathbilim.dto.ResultDto;
import kg.edu.mathbilim.model.olympiad.OlympiadApprovedList;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympiadStageDto {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    Long id;

    Integer stageOrder;

    LocalDate registrationStart;

    LocalDate registrationEnd;

    Date registrationDate;
    Date registrationEndDate;

    LocalDate startDate;

    LocalDate endDate;

    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt = LocalDateTime.now();

    List<ResultDto> result;

    List<OlympiadApprovedListDto> approvedList;

    public String getUpdatedAtFormatted() {
        return updatedAt != null ? updatedAt.format(FORMATTER) : "";
    }
}

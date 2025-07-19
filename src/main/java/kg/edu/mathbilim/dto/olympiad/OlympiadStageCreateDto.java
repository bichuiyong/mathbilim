package kg.edu.mathbilim.dto.olympiad;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kg.edu.mathbilim.validation.annotation.ValidOlympStage;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ValidOlympStage
public class OlympiadStageCreateDto {
    private Integer id;

    @NotNull(message = "Порядок этапа обязателен")
    @Positive(message = "Порядок этапа должен быть положительным числом")
    Integer stageOrder;

    @NotNull(message = "Дата начала регистрации обязательна")
    LocalDate registrationStart;

    @NotNull(message = "Дата окончания регистрации обязательна")
    LocalDate registrationEnd;

    @NotNull(message = "Дата начала события обязательна")
    LocalDate eventStartDate;

    @NotNull(message = "Дата окончания события обязательна")
    LocalDate eventEndDate;

    boolean hasStarted;
    boolean hasEnded;
}

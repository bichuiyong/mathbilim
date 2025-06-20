package kg.edu.mathbilim.dto.olympiad;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OlympiadStageCreateDto {

    @NotEmpty(message = "Название этапа обязательно")
    @Size(max = 255, message = "Название этапа не должно превышать 255 символов")
    private String name;

    @NotNull(message = "Порядок этапа обязателен")
    @Positive(message = "Порядок этапа должен быть положительным числом")
    private Integer stageOrder;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    @NotNull(message = "Дата начала регистрации обязательна")
    private LocalDate registrationStart;

    @NotNull(message = "Дата окончания регистрации обязательна")
    private LocalDate registrationEnd;

    @NotNull(message = "Дата начала события обязательна")
    private LocalDate eventStartDate;

    @NotNull(message = "Дата окончания события обязательна")
    private LocalDate eventEndDate;

    @Size(max = 500, message = "Местоположение не должно превышать 500 символов")
    private String location;
}

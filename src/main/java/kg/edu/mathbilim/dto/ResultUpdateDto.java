package kg.edu.mathbilim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO для обновления результата олимпиады")
public class ResultUpdateDto {

    @Positive(message = "ID файла должен быть положительным числом")
    private Long filesId;

    @Positive(message = "ID олимпиады должен быть положительным числом")
    private Long olympiadId;

    @Positive(message = "ID пользователя должен быть положительным числом")
    private Long userId;
}

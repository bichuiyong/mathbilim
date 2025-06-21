package kg.edu.mathbilim.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultCreateDto {

    @NotNull(message = "ID файла обязателен")
    @Positive(message = "ID файла должен быть положительным числом")
    private Long filesId;

    @NotNull(message = "ID олимпиады обязателен")
    @Positive(message = "ID олимпиады должен быть положительным числом")
    private Long olympiadId;

    @Positive(message = "ID пользователя должен быть положительным числом")
    private Long userId;
}

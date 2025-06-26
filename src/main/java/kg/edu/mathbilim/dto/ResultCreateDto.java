package kg.edu.mathbilim.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultCreateDto {

    @NotNull(message = "ID файла обязателен")
    @Positive(message = "ID файла должен быть положительным числом")
    Long filesId;

    @NotNull(message = "ID олимпиады обязателен")
    @Positive(message = "ID олимпиады должен быть положительным числом")
    Long olympiadId;
}

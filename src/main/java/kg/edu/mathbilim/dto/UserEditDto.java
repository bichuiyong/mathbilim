package kg.edu.mathbilim.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    private String surname;

    @NotNull(message = "Выберите тип аккаунта")
    @Positive(message = "Выберите тип аккаунта")
    private Integer typeId;
}

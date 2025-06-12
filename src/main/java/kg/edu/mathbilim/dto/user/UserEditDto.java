package kg.edu.mathbilim.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditDto {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    private String surname;
}

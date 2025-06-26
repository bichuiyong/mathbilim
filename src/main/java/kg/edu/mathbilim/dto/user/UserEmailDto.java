package kg.edu.mathbilim.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.validation.annotation.UniqueEmail;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailDto {
    @NotBlank(message = "Email обязателен")
    @Email(message = "Неверный формат email")
    @UniqueEmail
    private String email;
    @NotNull
    private Integer type;
}

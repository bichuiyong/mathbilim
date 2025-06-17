package kg.edu.mathbilim.dto.user;

import jakarta.validation.constraints.*;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.reference.RoleDto;
import kg.edu.mathbilim.dto.user.user_type.UserTypeDto;
import kg.edu.mathbilim.validation.annotation.UniqueEmail;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    @NotBlank(message = "Имя обязательно")
    private String name;

    private FileDto avatar;

    private String surname;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Неверный формат email")
    @UniqueEmail
    private String email;

    @NotBlank
    @Size(min = 6, max = 24, message = "Длина должна быть >= 6 и <= 24")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$",
            message = "Should contain at least one uppercase letter and one number"
    )
    private String password;

    @Builder.Default
    private Boolean enabled = true;

    @Builder.Default
    private Boolean isEmailVerified = false;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();

    private RoleDto role;

    @NotNull(message = "Выберите тип аккаунта")
    private UserTypeDto type;
}

package kg.edu.mathbilim.dto.user;

import jakarta.validation.constraints.*;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.reference.RoleDto;
import kg.edu.mathbilim.validation.annotation.UniqueEmail;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;

    @NotBlank(message = "Имя обязательно")
    String name;

    FileDto avatar;

    String surname;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Неверный формат email")
    @UniqueEmail
    String email;

    @NotBlank
    @Size(min = 6, max = 24, message = "Длина должна быть >= 6 и <= 24")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$",
            message = "Should contain at least one uppercase letter and one number"
    )
    String password;

    @Builder.Default
    Boolean enabled = true;

    @Builder.Default
    Boolean isEmailVerified = false;

    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    LocalDateTime updatedAt = LocalDateTime.now();

    RoleDto role;

    @NotNull(message = "Выберите тип аккаунта")
    UserTypeDto type;
}

package kg.edu.mathbilim.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import kg.edu.mathbilim.dto.reference.role.RoleDto;
import kg.edu.mathbilim.enums.UserType;
import kg.edu.mathbilim.validation.annotation.UniqueEmail;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEditByAdminDto {
    private Long id;

    @NotBlank(message = "Имя обязательно")
    private String name;

    private String surname;

    @NotNull(message = "Роль обязательна")
    private RoleDto role;

    @NotNull(message = "Выберите тип аккаунта")
    @Valid
    private UserType type;
}

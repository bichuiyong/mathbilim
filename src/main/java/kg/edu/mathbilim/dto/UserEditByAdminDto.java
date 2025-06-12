package kg.edu.mathbilim.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.reference.RoleDto;
import kg.edu.mathbilim.dto.reference.user_type.UserTypeDto;
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
    private UserTypeDto type;
}

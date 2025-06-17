package kg.edu.mathbilim.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.reference.RoleDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    private String surname;

    @NotNull(message = "Выберите тип аккаунта")
    private Integer typeId;

    private FileDto avatar;

    private RoleDto role;

    @Builder.Default
    private Instant updatedAt = Instant.now();
}

package kg.edu.mathbilim.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.reference.RoleDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEditDto {
    @NotBlank(message = "Имя не может быть пустым")
    String name;

    String surname;

    @NotNull(message = "Выберите тип аккаунта")
    Integer typeId;

    FileDto avatar;

    RoleDto role;

    @Builder.Default
    LocalDateTime updatedAt = LocalDateTime.now();
}

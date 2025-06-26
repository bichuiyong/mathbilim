package kg.edu.mathbilim.dto.user;

import jakarta.validation.constraints.NotBlank;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.reference.RoleDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEditDto {
    Long id;

    @NotBlank(message = "Имя не может быть пустым")
    String name;

    FileDto avatar;

    String surname;

    Integer typeId;

    RoleDto role;
}

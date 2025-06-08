package kg.edu.mathbilim.dto.reference.types;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTypeDto {
    @NotNull(message = "Выберите тип аккаунта")
    private Integer id;

    private String name;
}

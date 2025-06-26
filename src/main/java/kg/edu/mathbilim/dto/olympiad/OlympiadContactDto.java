package kg.edu.mathbilim.dto.olympiad;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympiadContactDto {
    @NotEmpty(message = "Информация обязательна")
    @Size(max = 1000, message = "Информация не должна превышать 1000 символов")
    String info;

    @NotNull
    Integer contactType;
}

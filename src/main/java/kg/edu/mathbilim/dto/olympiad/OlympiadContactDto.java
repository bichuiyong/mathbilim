package kg.edu.mathbilim.dto.olympiad;

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
    @Size(max = 1000, message = "Информация не должна превышать 1000 символов")
    String info;

    @NotNull
    Long contactType;
}

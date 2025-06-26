package kg.edu.mathbilim.dto.olympiad;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.dto.ContactTypeDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympiadContactDto {
    Long id;

    @NotEmpty(message = "Информация обязательна")
    @Size(max = 1000, message = "Информация не должна превышать 1000 символов")
    String info;

    OlympiadDto olympiad;

    ContactTypeDto contactDto;

    LocalDateTime createdAt;

}

package kg.edu.mathbilim.dto.olympiad;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OlympiadContactDto {
    private Long id;

    @NotEmpty(message = "Информация обязательна")
    @Size(max = 1000, message = "Информация не должна превышать 1000 символов")
    private String info;

    private OlympiadDto olympiad;

    private LocalDateTime createdAt;

}

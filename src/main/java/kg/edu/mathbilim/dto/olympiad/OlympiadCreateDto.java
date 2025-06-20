package kg.edu.mathbilim.dto.olympiad;

import kg.edu.mathbilim.dto.user.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OlympiadCreateDto {

    @NotEmpty(message = "Название олимпиады не должно быть пустым")
    @Size(max = 255, message = "Название олимпиады не должно превышать 255 символов")
    private String title;

    @Size(max = 1000, message = "Информация об олимпиаде не должна превышать 1000 символов")
    private String info;

    @Size(max = 1000, message = "Правила олимпиады не должны превышать 1000 символов")
    private String rules;

    @NotEmpty(message = "Предмет обязателен")
    @Size(max = 100, message = "Название предмета не должно превышать 100 символов")
    private String subject;

    @NotNull(message = "Дата начала обязательна")
    private LocalDate startDate;

    @NotNull(message = "Дата окончания обязательна")
    private LocalDate endDate;

    @Valid
    private OlympiadContactDto contact;

    @NotNull(message = "Создатель обязателен")
    @Valid
    private UserDto creator;

    @NotEmpty(message = "Этапы обязательны")
    @Valid
    private List<OlympiadStageCreateDto> stages;

    @Valid
    private List<OlympiadContactDto> contacts;
}

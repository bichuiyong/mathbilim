package kg.edu.mathbilim.dto.olympiad;

import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.user.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Organization;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympiadCreateDto {

    @NotEmpty(message = "Название олимпиады не должно быть пустым")
    @Size(max = 255, message = "Название олимпиады не должно превышать 255 символов")
    String title;

    @NotEmpty
    @Size(max = 1000, message = "Информация об олимпиаде не должна превышать 1000 символов")
    String info;

    @NotEmpty
    @Size(max = 1000, message = "Правила олимпиады не должны превышать 1000 символов")
    String rules;

    @NotNull(message = "Дата начала обязательна")
    LocalDate startDate;

    @NotNull
    MultipartFile imageFile;

    @NotNull(message = "Дата окончания обязательна")
    LocalDate endDate;

    @Valid
    List<OlympiadContactDto> contacts = new ArrayList<>();

    @NotNull(message = "Создатель обязателен")
    Long creatorId;

    @NotEmpty(message = "Этапы обязательны")
    @Valid
    List<OlympiadStageCreateDto> stages = new ArrayList<>();

    @NotNull
    List<Long> organizationIds = new ArrayList<>();
}

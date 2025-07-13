package kg.edu.mathbilim.dto.olympiad;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.dto.interfacePack.OnCreate;
import kg.edu.mathbilim.validation.annotation.ValidOlympiadDates;
import kg.edu.mathbilim.validation.annotation.ValidStageDates;
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
@ValidStageDates
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ValidOlympiadDates
public class OlympiadCreateDto {
    Long id;

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

    @NotNull(groups = OnCreate.class)
    MultipartFile imageFile;

    @NotNull(message = "Дата окончания обязательна")
    LocalDate endDate;

    @Valid
    @Builder.Default
    List<OlympiadContactDto> contacts = new ArrayList<>();

    @NotNull(message = "Создатель обязателен")
    Long creatorId;

    @Builder.Default
    @NotNull(message = "Этапы обязательны")
    @Valid
    List<OlympiadStageCreateDto> stages = new ArrayList<>();

    @Builder.Default
    List<Long> organizationIds = new ArrayList<>();

    boolean hasStarted;
    boolean hasEnded;
}

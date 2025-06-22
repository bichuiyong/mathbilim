package kg.edu.mathbilim.dto.user;

import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTypeDto {
    Integer id;

    @AllTranslationsRequired
    List<UserTypeTranslationDto> userTypeTranslations;
}

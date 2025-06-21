package kg.edu.mathbilim.dto.user;

import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTypeDto {
    private Integer id;

    @AllTranslationsRequired
    private List<UserTypeTranslationDto> userTypeTranslations;
}

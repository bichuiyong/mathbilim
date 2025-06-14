package kg.edu.mathbilim.dto.user.user_type;

import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeDto {
    private Integer id;

    @AllTranslationsRequired
    private List<UserTypeTranslationDto> userTypeTranslations;
}

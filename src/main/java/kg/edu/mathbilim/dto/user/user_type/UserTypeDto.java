package kg.edu.mathbilim.dto.user.user_type;

import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserTypeDto {
    private Integer id;

    @AllTranslationsRequired
    private List<UserTypeTranslationDto> userTypeTranslations;
}

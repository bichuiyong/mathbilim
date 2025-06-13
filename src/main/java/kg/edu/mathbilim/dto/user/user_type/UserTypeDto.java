package kg.edu.mathbilim.dto.user.user_type;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserTypeDto {
    private Integer id;

    private List<UserTypeTranslationDto> userTypeTranslations;
}

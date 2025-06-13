package kg.edu.mathbilim.dto.user.user_type;

import kg.edu.mathbilim.dto.translations.TypeTranslationDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserTypeTranslationDto extends TypeTranslationDto {
    private Integer userTypeId;
}
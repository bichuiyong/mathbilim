package kg.edu.mathbilim.dto.user;

import kg.edu.mathbilim.dto.abstracts.TypeTranslationDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class UserTypeTranslationDto extends TypeTranslationDto {
    Integer userTypeId;
}
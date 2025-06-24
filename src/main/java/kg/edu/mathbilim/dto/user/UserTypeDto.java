package kg.edu.mathbilim.dto.user;

import kg.edu.mathbilim.dto.abstracts.BaseTypeDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTypeDto extends BaseTypeDto<UserTypeTranslationDto> {

}

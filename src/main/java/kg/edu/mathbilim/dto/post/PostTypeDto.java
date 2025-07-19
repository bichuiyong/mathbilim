package kg.edu.mathbilim.dto.post;


import kg.edu.mathbilim.dto.abstracts.BaseTypeDto;
import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostTypeDto extends BaseTypeDto<PostTypeTranslationDto> {

    public PostTypeDto() {
        super();
    }
}

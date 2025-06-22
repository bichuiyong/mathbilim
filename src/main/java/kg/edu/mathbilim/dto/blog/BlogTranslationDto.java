package kg.edu.mathbilim.dto.blog;

import kg.edu.mathbilim.dto.abstracts.ContentTranslationDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogTranslationDto extends ContentTranslationDto {
    Long blogId;

}

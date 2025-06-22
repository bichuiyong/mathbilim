package kg.edu.mathbilim.dto.news;

import kg.edu.mathbilim.dto.abstracts.ContentTranslationDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsTranslationDto extends ContentTranslationDto {
    Long newsId;
}

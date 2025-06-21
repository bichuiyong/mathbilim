package kg.edu.mathbilim.dto.news;

import kg.edu.mathbilim.dto.abstracts.ContentTranslationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class NewsTranslationDto extends ContentTranslationDto {
    Long newsId;
}

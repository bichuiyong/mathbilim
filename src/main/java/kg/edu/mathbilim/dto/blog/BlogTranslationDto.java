package kg.edu.mathbilim.dto.blog;

import kg.edu.mathbilim.dto.abstracts.ContentTranslationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogTranslationDto extends ContentTranslationDto {
    Long blogId;

}

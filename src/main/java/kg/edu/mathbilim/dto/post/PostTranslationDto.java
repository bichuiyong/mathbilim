package kg.edu.mathbilim.dto.post;

import kg.edu.mathbilim.dto.translations.ContentTranslationDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PostTranslationDto extends ContentTranslationDto {

    Long postId;
}
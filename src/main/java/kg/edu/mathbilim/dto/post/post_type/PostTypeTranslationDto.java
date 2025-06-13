package kg.edu.mathbilim.dto.post.post_type;

import kg.edu.mathbilim.dto.translations.TypeTranslationDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PostTypeTranslationDto extends TypeTranslationDto {
    private Integer postTypeId;
}
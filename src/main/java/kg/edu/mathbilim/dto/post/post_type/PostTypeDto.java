package kg.edu.mathbilim.dto.post.post_type;


import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTypeDto {
    private Integer id;

    @AllTranslationsRequired
    private List<PostTypeTranslationDto> postTypeTranslations;
}

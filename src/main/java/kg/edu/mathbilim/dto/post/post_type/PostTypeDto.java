package kg.edu.mathbilim.dto.post.post_type;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTypeDto {
    private Integer id;

    private List<PostTypeTranslationDto> postTypeTranslations;

}

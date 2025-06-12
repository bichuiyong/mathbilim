package kg.edu.mathbilim.dto.reference.post_type;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTypeDto {
    private Integer id;

    private Set<PostTypeTranslationDto> postTypeTranslations;

}

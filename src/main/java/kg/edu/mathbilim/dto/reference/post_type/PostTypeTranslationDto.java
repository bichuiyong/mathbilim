package kg.edu.mathbilim.dto.reference.post_type;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTypeTranslationDto {
    private Integer postTypeId;

    @NotBlank
    private String languageCode;

    @NotBlank
    private String translation;
}
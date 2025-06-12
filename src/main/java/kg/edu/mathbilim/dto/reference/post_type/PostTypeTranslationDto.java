package kg.edu.mathbilim.dto.reference.post_type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTypeTranslationDto {
    @NotNull
    private Integer postTypeId;

    @NotBlank
    private String languageCode;

    @NotBlank
    private String translation;
}
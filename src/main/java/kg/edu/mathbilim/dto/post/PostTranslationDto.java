package kg.edu.mathbilim.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTranslationDto {

    Long postId;

    @NotBlank
    private String languageCode;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
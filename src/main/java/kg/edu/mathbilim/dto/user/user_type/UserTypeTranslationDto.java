package kg.edu.mathbilim.dto.user.user_type;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTypeTranslationDto {
    private Integer userTypeId;

    @NotBlank
    private String languageCode;

    @NotBlank
    private String translation;
}
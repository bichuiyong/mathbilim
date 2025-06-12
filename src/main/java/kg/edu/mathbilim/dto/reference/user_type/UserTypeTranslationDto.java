package kg.edu.mathbilim.dto.reference.user_type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTypeTranslationDto {
    @NotNull
    private Integer userTypeId;

    @NotBlank
    private String languageCode;

    @NotBlank
    private String translation;
}
package kg.edu.mathbilim.dto.reference.event_type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventTypeTranslationDto {
    @NotNull
    private Integer eventTypeId;

    @NotBlank
    private String languageCode;

    @NotBlank
    private String translation;
}
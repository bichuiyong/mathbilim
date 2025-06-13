package kg.edu.mathbilim.dto.reference.event_type;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventTypeTranslationDto {

    private Integer eventTypeId;

    @NotBlank
    private String languageCode;

    @NotBlank
    private String translation;
}
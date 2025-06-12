package kg.edu.mathbilim.dto.event;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventTranslationDto {

    Long eventId;

    @NotBlank
    private String languageCode;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
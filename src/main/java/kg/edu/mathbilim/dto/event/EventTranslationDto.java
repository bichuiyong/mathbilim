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

    private String languageCode;

    private String title;

    private String content;
}
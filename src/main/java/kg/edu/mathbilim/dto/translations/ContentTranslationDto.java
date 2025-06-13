package kg.edu.mathbilim.dto.translations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class ContentTranslationDto {
    private String languageCode;

    private String title;

    private String content;
}

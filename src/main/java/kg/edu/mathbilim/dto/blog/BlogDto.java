package kg.edu.mathbilim.dto.blog;

import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.util.TranslationUtil;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogDto extends ContentDto {

    @AtLeastOneTranslationRequired
    @Builder.Default
    List<BlogTranslationDto> blogTranslations = createDefaultTranslations();

    static List<BlogTranslationDto> createDefaultTranslations() {
        return TranslationUtil.createDefaultTranslations(languageCode ->
                BlogTranslationDto.builder()
                        .languageCode(languageCode)
                        .build()
        );
    }
}

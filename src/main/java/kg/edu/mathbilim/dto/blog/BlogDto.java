package kg.edu.mathbilim.dto.blog;

import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto extends ContentDto {

    @AtLeastOneTranslationRequired
    @Builder.Default
    private List<BlogTranslationDto> blogTranslations = createDefaultTranslations();

    private static List<BlogTranslationDto> createDefaultTranslations() {
        return Arrays.stream(Language.values())
                .map(lang -> BlogTranslationDto.builder()
                        .languageCode(lang.getCode())
                        .title("")
                        .content("")
                        .build())
                .collect(Collectors.toList());
    }
}

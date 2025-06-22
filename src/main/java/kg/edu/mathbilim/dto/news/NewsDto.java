package kg.edu.mathbilim.dto.news;


import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.abstracts.AdminContentDto;
import kg.edu.mathbilim.util.TranslationUtil;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsDto extends AdminContentDto {
    
    List<FileDto> newsFiles = new ArrayList<>();

    @AtLeastOneTranslationRequired
    @Builder.Default
    List<NewsTranslationDto> newsTranslations = createDefaultTranslations();

    static List<NewsTranslationDto> createDefaultTranslations() {
        return TranslationUtil.createDefaultTranslations(languageCode ->
                NewsTranslationDto.builder()
                        .languageCode(languageCode)
                        .build()
        );
    }
}

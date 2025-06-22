package kg.edu.mathbilim.dto.post;

import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.util.TranslationUtil;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto extends ContentDto {
    @NotNull
    Long typeId;

    List<FileDto> postFiles = new ArrayList<>();

    @AtLeastOneTranslationRequired
    @Builder.Default
    List<PostTranslationDto> postTranslations = createDefaultTranslations();

    static List<PostTranslationDto> createDefaultTranslations() {
        return TranslationUtil.createDefaultTranslations(languageCode ->
                PostTranslationDto.builder()
                        .languageCode(languageCode)
                        .build()
        );
    }
}
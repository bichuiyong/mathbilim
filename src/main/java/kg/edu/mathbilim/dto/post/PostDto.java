package kg.edu.mathbilim.dto.post;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.util.TranslationUtil;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto extends ContentDto {
    @NotNull(message = "Выберите тип поста")
    Long typeId;

    List<FileDto> postFiles = new ArrayList<>();

    @AtLeastOneTranslationRequired
    @Builder.Default
    List<@Valid PostTranslationDto> postTranslations = createDefaultTranslations();

    static List<PostTranslationDto> createDefaultTranslations() {
        return TranslationUtil.createDefaultTranslations(languageCode ->
                PostTranslationDto.builder()
                        .languageCode(languageCode)
                        .build()
        );
    }
}
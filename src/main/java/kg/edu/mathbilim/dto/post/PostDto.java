package kg.edu.mathbilim.dto.post;

import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import lombok.*;
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
public class PostDto extends ContentDto {
    @NotNull
    private Long typeId;

    List<FileDto> postFiles = new ArrayList<>();

    @AtLeastOneTranslationRequired
    @Builder.Default
    private List<PostTranslationDto> postTranslations = createDefaultTranslations();

    private static List<PostTranslationDto> createDefaultTranslations() {
        return Arrays.stream(Language.values())
                .map(lang -> PostTranslationDto.builder()
                        .languageCode(lang.getCode())
                        .title("")
                        .content("")
                        .build())
                .collect(Collectors.toList());
    }
}
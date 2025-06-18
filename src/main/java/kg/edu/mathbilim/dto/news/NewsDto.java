package kg.edu.mathbilim.dto.news;


import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.post.PostTranslationDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.news.NewsTranslationId;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {
    private Long id;


    private UserDto user;


    @Builder.Default
    private Instant createdTime = Instant.now();

    @Builder.Default
    private Instant updatedTime = Instant.now();

    @Builder.Default
    private Long viewCount = 0L;


    private FileDto mainImage;

    List<FileDto> files = new ArrayList<>();

    @AtLeastOneTranslationRequired
    @Builder.Default
    private List<NewsTranslationDto> newsTranslationDto = createDefaultTranslations();

    private static List<NewsTranslationDto> createDefaultTranslations() {
        return Arrays.stream(Language.values())
                .map(lang -> NewsTranslationDto.builder()
                        .languageCode(lang.getCode())
                        .title("")
                        .content("")
                        .build())
                .collect(Collectors.toList());
    }
}

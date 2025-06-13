package kg.edu.mathbilim.dto.post;

import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.Language;
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
public class PostDto {
    private Long id;

    @NotNull
    private Long typeId;

    private UserDto user;

    @Builder.Default
    private UserDto approvedBy = null;

    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();

    @Builder.Default
    private Long viewCount = 0L;

    @Builder.Default
    private Long shareCount = 0L;

    private FileDto mainImage;

    List<FileDto> files = new ArrayList<>();

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
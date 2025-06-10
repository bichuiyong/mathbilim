package kg.edu.mathbilim.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.reference.PostTypeDto;
import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;

    @NotNull
    private PostTypeDto type;

    @NotBlank
    private String title;

    private FileDto mainImage;

    @NotBlank
    private String slug;

    @NotBlank
    private String content;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();

    @Builder.Default
    private Long viewCount = 0L;

    @Builder.Default
    private Long shareCount = 0L;

    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;

    @NotNull
    private UserDto user;

    @Builder.Default
    private UserDto approvedBy = null;

    Set<FileDto> files = new LinkedHashSet<>();
}
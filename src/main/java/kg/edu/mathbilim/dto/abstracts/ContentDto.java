package kg.edu.mathbilim.dto.abstracts;

import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ContentDto {
    Long id;

    UserDto creator;

    @Builder.Default
    private UserDto approvedBy = null;

    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;

    @Builder.Default
    Instant createdAt = Instant.now();

    @Builder.Default
    Instant updatedAt = Instant.now();

    @NotNull
    @Builder.Default
    Long viewCount;

    @NotNull
    @Builder.Default
    Long shareCount = 0L;

    FileDto mainImage;
}

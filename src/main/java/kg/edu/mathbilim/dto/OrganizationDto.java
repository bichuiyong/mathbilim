package kg.edu.mathbilim.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationDto {
    Long id;

    @NotBlank
    String name;

    @NotBlank
    String description;

    String url;

    FileDto avatar;

    @NotNull
    UserDto creator;

    @Builder.Default
    UserDto approvedBy = null;

    @NotNull
    @Builder.Default
    ContentStatus status = ContentStatus.DRAFT;

    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    LocalDateTime updatedAt = LocalDateTime.now();
}
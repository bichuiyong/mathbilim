package kg.edu.mathbilim.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String url;

    private String avatar;

    @NotNull
    private UserDto creator;

    @Builder.Default
    private UserDto approvedBy = null;

    @NotNull
    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();
}
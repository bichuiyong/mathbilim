package kg.edu.mathbilim.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.reference.event_type.EventTypeDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.validation.annotation.ValidDateTimeRange;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidDateTimeRange(
        startDateTimeField = "startDate",
        endDateTimeField = "endDate"
)
public class EventDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String content;

    private FileDto mainImage;

    private Map<String, Object> metadata;

    private ContentStatus status;

    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;

    @NotNull
    private EventTypeDto type;

    @NotNull
    private UserDto user;

    @Builder.Default
    private UserDto approvedBy = null;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();

    @Builder.Default
    private Set<FileDto> files = new LinkedHashSet<>();
}
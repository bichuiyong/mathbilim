package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
    private Long id;

    private String name;

    private String content;

    private Map<String, Object> metadata;

    private ContentStatus status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private EventTypeDto type;

    private UserDto user;

    private UserDto approvedBy;

    private Instant createdAt;

    private Instant updatedAt;

    private Set<FileDto> files;
}
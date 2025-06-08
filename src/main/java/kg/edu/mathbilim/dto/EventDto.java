package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.dto.reference.types.EventTypeDto;
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

    private Map<String, Object> content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private EventTypeDto type;

    private UserDto user;

    private UserDto approvedBy;

    private Instant createdAt;

    private Instant updatedAt;

    private Set<FileDto> files;
}
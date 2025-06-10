package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.validation.annotation.EventType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventTypeDto {
    private Integer id;

    @EventType
    private String name;
}

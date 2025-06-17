package kg.edu.mathbilim.mapper.event;

import kg.edu.mathbilim.dto.event.event_type.EventTypeDto;
import kg.edu.mathbilim.model.event.event_type.EventType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventTypeMapper {
    EventType toEntity(EventTypeDto dto);

    EventTypeDto toDto(EventType event);
}

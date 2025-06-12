package kg.edu.mathbilim.mapper.reference.event_type;

import kg.edu.mathbilim.dto.reference.event_type.EventTypeDto;
import kg.edu.mathbilim.model.reference.event_type.EventType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventTypeMapper {
    EventType toEntity(EventTypeDto dto);

    EventTypeDto toDto(EventType event);
}

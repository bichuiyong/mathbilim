package kg.edu.mathbilim.mapper.event;

import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.model.event.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventDto dto);

    EventDto toDto(Event event);
}

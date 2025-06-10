package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.EventDto;
import kg.edu.mathbilim.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventDto dto);

    EventDto toDto(Event event);
}

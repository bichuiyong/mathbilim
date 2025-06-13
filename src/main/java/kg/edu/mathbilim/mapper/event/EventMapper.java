package kg.edu.mathbilim.mapper.event;

import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.model.event.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "type.id", source = "typeId")
    Event toEntity(EventDto dto);

    @Mapping(target = "typeId", source = "type.id")
    EventDto toDto(Event event);
}

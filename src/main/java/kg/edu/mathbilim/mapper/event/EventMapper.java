package kg.edu.mathbilim.mapper.event;

import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.event.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper extends BaseMapper<Event, EventDto> {
    @Mapping(target = "type.id", source = "typeId")
    Event toEntity(EventDto dto);

    @Mapping(target = "typeId", source = "type.id")
    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "deleted", source = "deleted")
    EventDto toDto(Event event);
}

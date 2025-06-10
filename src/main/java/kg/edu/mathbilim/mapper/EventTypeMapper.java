package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.EventTypeDto;
import kg.edu.mathbilim.model.EventType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventTypeMapper {
    EventType toEntity(EventTypeDto dto);

    EventTypeDto toDto(EventType event);
}

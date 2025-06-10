package kg.edu.mathbilim.mapper.reference;

import kg.edu.mathbilim.dto.reference.EventTypeDto;
import kg.edu.mathbilim.model.reference.EventType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventTypeMapper {
    EventType toEntity(EventTypeDto dto);

    EventTypeDto toDto(EventType event);
}

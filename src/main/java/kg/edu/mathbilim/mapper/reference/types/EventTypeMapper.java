package kg.edu.mathbilim.mapper.reference.types;

import kg.edu.mathbilim.dto.reference.types.EventTypeDto;
import kg.edu.mathbilim.model.reference.types.EventType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventTypeMapper {
    EventType toEntity(EventTypeDto dto);

    EventTypeDto toDto(EventType entity);
}

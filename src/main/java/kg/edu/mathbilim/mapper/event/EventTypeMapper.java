package kg.edu.mathbilim.mapper.event;

import kg.edu.mathbilim.dto.event.EventTypeDto;
import kg.edu.mathbilim.dto.event.EventTypeTranslationDto;
import kg.edu.mathbilim.mapper.TypeBaseMapper;
import kg.edu.mathbilim.model.event.EventType;
import kg.edu.mathbilim.model.event.EventTypeTranslation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventTypeMapper  extends TypeBaseMapper<
        EventType,
        EventTypeDto,
        EventTypeTranslation,
        EventTypeTranslationDto,
        EventTypeTranslationMapper> {

        @Override
        EventTypeDto toDto(EventType entity);

        @Override
        EventType toEntity(EventTypeDto dto);

        @Override
        default EventTypeTranslationMapper getTranslationMapper() {
            return new EventTypeTranslationMapperImpl();
        }
}

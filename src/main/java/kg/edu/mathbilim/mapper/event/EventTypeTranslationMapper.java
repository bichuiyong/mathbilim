package kg.edu.mathbilim.mapper.event;

import kg.edu.mathbilim.dto.event.EventTypeTranslationDto;
import kg.edu.mathbilim.mapper.BaseTranslationMapper;
import kg.edu.mathbilim.model.event.EventType;
import kg.edu.mathbilim.model.event.EventTypeTranslation;
import kg.edu.mathbilim.model.abstracts.TypeTranslation.TranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventTypeTranslationMapper extends BaseTranslationMapper<EventTypeTranslation, EventTypeTranslationDto> {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "eventTypeId")
    EventTypeTranslationDto toDto(EventTypeTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "eventTypeId")
    @Mapping(target = "parent.id", source = "eventTypeId")
    EventTypeTranslation toEntity(EventTypeTranslationDto dto);
}
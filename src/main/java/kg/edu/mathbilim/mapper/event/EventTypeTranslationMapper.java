package kg.edu.mathbilim.mapper.event;

import kg.edu.mathbilim.dto.event.EventTypeTranslationDto;
import kg.edu.mathbilim.mapper.BaseTranslationMapper;
import kg.edu.mathbilim.model.event.EventTypeTranslation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventTypeTranslationMapper extends BaseTranslationMapper<EventTypeTranslation, EventTypeTranslationDto> {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "typeId")
    EventTypeTranslationDto toDto(EventTypeTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "typeId")
    @Mapping(target = "parent.id", source = "typeId")
    EventTypeTranslation toEntity(EventTypeTranslationDto dto);
}
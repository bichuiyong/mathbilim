package kg.edu.mathbilim.mapper.event;

import kg.edu.mathbilim.dto.event.EventTypeTranslationDto;
import kg.edu.mathbilim.model.event.EventType;
import kg.edu.mathbilim.model.event.EventTypeTranslation;
import kg.edu.mathbilim.model.abstracts.TypeTranslation.TranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventTypeTranslationMapper {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "eventTypeId")
    @Mapping(source = "translation", target = "translation")
    EventTypeTranslationDto toDto(EventTypeTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "eventTypeId")
    @Mapping(target = "translation", source = "translation")
    @Mapping(target = "eventType.id", source = "eventTypeId")
    @Mapping(target = "eventType.eventTypeTranslations", ignore = true)
    EventTypeTranslation toEntity(EventTypeTranslationDto dto);

    @AfterMapping
    default void ensureCompositeKey(@MappingTarget EventTypeTranslation entity, EventTypeTranslationDto dto) {
        if (entity.getId() == null) {
            entity.setId(new TranslationId());
        }
        entity.getId().setTypeId(dto.getEventTypeId());
        entity.getId().setLanguageCode(dto.getLanguageCode());

        if (entity.getEventType() == null) {
            entity.setEventType(new EventType());
        }
        entity.getEventType().setId(dto.getEventTypeId());
    }
}
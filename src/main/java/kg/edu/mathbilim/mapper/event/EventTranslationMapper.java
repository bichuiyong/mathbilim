package kg.edu.mathbilim.mapper.event;

import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.model.event.EventTranslation;
import kg.edu.mathbilim.model.event.EventTranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventTranslationMapper {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.eventId", target = "eventId")
    EventTranslationDto toDto(EventTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.eventId", source = "eventId")
    @Mapping(target = "event.id", source = "eventId")
    @Mapping(target = "event.eventTranslations", ignore = true)
    EventTranslation toEntity(EventTranslationDto dto);

    @AfterMapping
    default void ensureCompositeKey(@MappingTarget EventTranslation entity, EventTranslationDto dto) {
        if (entity.getId() == null) {
            entity.setId(new EventTranslationId());
            entity.getId().setEventId(dto.getEventId());
            entity.getId().setLanguageCode(dto.getLanguageCode());
        }
    }
}

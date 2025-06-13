package kg.edu.mathbilim.service.interfaces.reference.event_type;

import kg.edu.mathbilim.dto.reference.event_type.EventTypeTranslationDto;
import kg.edu.mathbilim.model.reference.event_type.EventTypeTranslation;

import java.util.List;

public interface EventTypeTranslationService {
    List<EventTypeTranslationDto> getTranslationsByEventTypeId(Integer eventTypeId);

    EventTypeTranslation getTranslationEntity(Integer eventTypeId, String languageCode);

    EventTypeTranslationDto getTranslation(Integer eventTypeId, String languageCode);

    List<EventTypeTranslationDto> getTranslationsByLanguage(String languageCode);

    EventTypeTranslationDto createTranslation(EventTypeTranslationDto dto);

    EventTypeTranslationDto updateTranslation(Integer eventTypeId, String languageCode, String newTranslation);

    EventTypeTranslationDto upsertTranslation(EventTypeTranslationDto dto);

    void deleteTranslation(Integer eventTypeId, String languageCode);

    void deleteAllTranslationsByEventTypeId(Integer eventTypeId);

    boolean existsTranslation(Integer eventTypeId, String languageCode);
}

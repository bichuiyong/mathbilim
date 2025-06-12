package kg.edu.mathbilim.service.interfaces.event;

import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.model.event.EventTranslation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface EventTranslationService {
    List<EventTranslationDto> getTranslationsByEventId(Long eventId);

    EventTranslation getTranslationEntity(Long eventId, String languageCode);

    EventTranslationDto getTranslation(Long eventId, String languageCode);

    List<EventTranslationDto> getTranslationsByLanguage(String languageCode);

    @Transactional
    EventTranslationDto createTranslation(EventTranslationDto dto);

    @Transactional
    EventTranslationDto updateTranslation(Long eventId, String languageCode, String title, String content);

    @Transactional
    EventTranslationDto upsertTranslation(EventTranslationDto dto);

    @Transactional
    void deleteTranslation(Long eventId, String languageCode);

    @Transactional
    void deleteAllTranslationsByEventId(Long eventId);

    boolean existsTranslation(Long eventId, String languageCode);

    @Transactional
    void saveTranslations(Long eventId, Set<EventTranslationDto> translations);
}

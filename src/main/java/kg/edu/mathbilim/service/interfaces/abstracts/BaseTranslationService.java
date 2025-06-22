package kg.edu.mathbilim.service.interfaces.abstracts;

import kg.edu.mathbilim.dto.abstracts.ContentTranslationDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface BaseTranslationService<T extends ContentTranslationDto> {
    void saveTranslations(Long entityId, Set<T> translations);
    T upsertTranslation(T dto);
    T createTranslation(T dto);
    T updateTranslation(Long entityId, String languageCode, String title, String content);

    @Transactional
    void deleteAllTranslationsByEntityId(Long entityId);
}

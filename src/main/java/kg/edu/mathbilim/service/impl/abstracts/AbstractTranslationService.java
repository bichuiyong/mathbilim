package kg.edu.mathbilim.service.impl.abstracts;

import kg.edu.mathbilim.dto.abstracts.ContentTranslationDto;
import kg.edu.mathbilim.exception.nsee.TranslationNotFoundException;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.abstracts.ContentTranslation;
import kg.edu.mathbilim.repository.abstracts.BaseTranslationRepository;
import kg.edu.mathbilim.service.interfaces.abstracts.BaseTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractTranslationService<
        T extends ContentTranslationDto,
        E extends ContentTranslation,
        ID,
        R extends BaseTranslationRepository<E, ID>,
        M extends BaseMapper<E, T>
        > implements BaseTranslationService<T> {

    protected final R repository;
    protected final M mapper;

    protected abstract ID createTranslationId(Long entityId, String languageCode);
    protected abstract void setEntityId(T dto, Long entityId);
    protected abstract String getEntityName();
    protected abstract Long getEntityIdFromDto(T dto);
    protected abstract void deleteAllTranslationsByEntityIdImpl(Long entityId);

    protected E getTranslationEntity(Long entityId, String languageCode) {
        ID id = createTranslationId(entityId, languageCode);
        return repository.findById(id)
                .orElseThrow(() -> new TranslationNotFoundException(
                        "Перевод для этого " + getEntityName() + " не был найден"));
    }

    protected T getTranslationDto(Long entityId, String languageCode) {
        return mapper.toDto(getTranslationEntity(entityId, languageCode));
    }

    @Transactional
    public void deleteTranslation(Long entityId, String languageCode) {
        ID id = createTranslationId(entityId, languageCode);
        repository.deleteById(id);
    }

    protected boolean existsTranslation(Long entityId, String languageCode) {
        ID id = createTranslationId(entityId, languageCode);
        return repository.existsById(id);
    }

    @Override
    @Transactional
    public void saveTranslations(Long entityId, Set<T> translations) {
        if (translations == null || translations.isEmpty()) {
            return;
        }

        for (T translation : translations) {
            if (isTranslationValid(translation)) {
                setEntityId(translation, entityId);
                upsertTranslation(translation);
            }
        }

        log.info("Saved {} translations for {} {}", translations.size(), getEntityName(), entityId);
    }

    @Override
    @Transactional
    public T upsertTranslation(T dto) {
        ID id = createTranslationId(getEntityIdFromDto(dto), dto.getLanguageCode());
        boolean exists = repository.existsById(id);

        if (exists) {
            return updateTranslation(getEntityIdFromDto(dto), dto.getLanguageCode(),
                    dto.getTitle(), dto.getContent());
        } else {
            return createTranslation(dto);
        }
    }

    @Override
    @Transactional
    public T createTranslation(T dto) {
        E translation = mapper.toEntity(dto);
        repository.save(translation);
        log.info("Save translation to language {}: {}",
                dto.getLanguageCode(), translation.getTitle());
        return dto;
    }

    @Override
    @Transactional
    public T updateTranslation(Long entityId, String languageCode, String title, String content) {
        E translation = getTranslationEntity(entityId, languageCode);
        translation.setTitle(title);
        translation.setContent(content);
        E saved = repository.save(translation);
        log.info("Updated translation to language {}: {}", languageCode, title);
        return mapper.toDto(saved);
    }

    @Transactional
    @Override
    public void deleteAllTranslationsByEntityId(Long entityId) {
        deleteAllTranslationsByEntityIdImpl(entityId);
    }

    protected boolean isTranslationValid(T translation) {
        return translation.getTitle() != null && !translation.getTitle().trim().isEmpty() &&
                translation.getContent() != null && !translation.getContent().trim().isEmpty();
    }
}

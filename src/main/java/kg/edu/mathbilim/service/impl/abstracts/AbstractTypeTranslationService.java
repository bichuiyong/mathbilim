package kg.edu.mathbilim.service.impl.abstracts;

import kg.edu.mathbilim.dto.abstracts.TypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TranslationNotFoundException;
import kg.edu.mathbilim.mapper.BaseTranslationMapper;
import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeTranslationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public abstract class AbstractTypeTranslationService<
        E extends TypeTranslation<?>,
        D extends TypeTranslationDto,
        R extends AbstractTypeTranslationRepository<E>,
        M extends BaseTranslationMapper<E, D>> {

    protected final R repository;
    protected final M mapper;

    protected AbstractTypeTranslationService(R repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<D> getTranslationsByTypeId(Integer typeId) {
        return repository.findByTypeId(typeId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public E getTranslationEntity(Integer typeId, String languageCode) {
        return repository.findByTypeIdAndLanguageCode(typeId, languageCode)
                .orElseThrow(() -> new TranslationNotFoundException(getNotFoundMessage()));
    }

    @Transactional(readOnly = true)
    public D getTranslation(Integer typeId, String languageCode) {
        return mapper.toDto(getTranslationEntity(typeId, languageCode));
    }

    @Transactional(readOnly = true)
    public List<D> getTranslationsByLanguage(String languageCode) {
        return repository.findByLanguageCode(languageCode)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public D createTranslation(D dto) {
        E translation = mapper.toEntity(dto);
        E saved = repository.save(translation);
        log.info("Saved translation for type {} in language {}", dto.getTypeId(), dto.getLanguageCode());
        return mapper.toDto(saved);
    }

    @Transactional
    public D updateTranslation(Integer typeId, String languageCode, String newTranslation) {
        E translation = getTranslationEntity(typeId, languageCode);
        translation.setTranslation(newTranslation);
        E saved = repository.save(translation);
        log.info("Updated translation for type {} in language {}", typeId, languageCode);
        return mapper.toDto(saved);
    }

    @Transactional
    public D upsertTranslation(D dto) {
        if (existsTranslation(dto.getTypeId(), dto.getLanguageCode())) {
            return updateTranslation(dto.getTypeId(), dto.getLanguageCode(), dto.getTranslation());
        }
        return createTranslation(dto);
    }

    @Transactional
    public void deleteTranslation(Integer typeId, String languageCode) {
        TypeTranslation.TranslationId id = new TypeTranslation.TranslationId();
        id.setTypeId(typeId);
        id.setLanguageCode(languageCode);
        repository.deleteById(id);
        log.info("Deleted translation for type {} in language {}", typeId, languageCode);
    }

    @Transactional
    public void deleteAllTranslationsByTypeId(Integer typeId) {
        repository.deleteByTypeId(typeId);
        log.info("Deleted all translations for type {}", typeId);
    }

    @Transactional(readOnly = true)
    public boolean existsTranslation(Integer typeId, String languageCode) {
        return repository.existsByTypeIdAndLanguageCode(typeId, languageCode);
    }

    protected abstract String getNotFoundMessage();
}
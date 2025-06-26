package kg.edu.mathbilim.service.impl.abstracts;

import kg.edu.mathbilim.dto.abstracts.BaseTypeDto;
import kg.edu.mathbilim.dto.abstracts.TypeTranslationDto;
import kg.edu.mathbilim.exception.nsee.TypeNotFoundException;
import kg.edu.mathbilim.mapper.BaseTranslationMapper;
import kg.edu.mathbilim.mapper.TypeBaseMapper;
import kg.edu.mathbilim.model.abstracts.BaseType;
import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeRepository;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeTranslationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractTypeContentService<
        E extends BaseType<T>,
        D extends BaseTypeDto<DT>,
        T extends TypeTranslation<E>,
        DT extends TypeTranslationDto,
        R extends AbstractTypeRepository<E>,
        TR extends AbstractTypeTranslationRepository<T>,
        M extends TypeBaseMapper<E, D, T, DT, ? extends BaseTranslationMapper<T, DT>>> {

    protected final R repository;
    protected final TR translationRepository;
    protected final M mapper;

    protected AbstractTypeContentService(R repository, TR translationRepository, M mapper) {
        this.repository = repository;
        this.translationRepository = translationRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<D> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<D> getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public D getByIdOrThrow(Integer id) {
        return getById(id)
                .orElseThrow(TypeNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public E getEntity(Integer id) {
        return repository.findById(id)
                .orElseThrow(TypeNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<D> getByLanguage(String languageCode) {
        return repository.findAll().stream()
                .map(entity -> {
                    D dto = mapper.toDto(entity);
                    getTranslation(entity.getId(), languageCode)
                            .ifPresent(translation -> dto.setTranslations(List.of(translation)));
                    return dto;
                })
                .toList();
    }

    @Transactional
    public D create(D dto) {
        E entity = createNewEntity();
        E saved = repository.save(entity);

        D savedDto = mapper.toDto(saved);

        if (dto.getTranslations() != null && !dto.getTranslations().isEmpty()) {
            saveTranslations(saved, dto.getTranslations());
            savedDto.setTranslations(dto.getTranslations());
        }

        return savedDto;
    }

    @Transactional
    public D update(Integer id, D dto) {
        getEntity(id);

        if (dto.getTranslations() != null) {
            E entity = getEntity(id);
            saveTranslations(entity, dto.getTranslations());
        }

        return getByIdOrThrow(id);
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Transactional
    public D addTranslation(Integer typeId, String languageCode, String translation) {
        E entity = getEntity(typeId);

        DT translationDto = createTranslationDto(typeId, languageCode, translation);
        T translationEntity = mapper.toTranslationEntity(translationDto, entity);
        translationRepository.save(translationEntity);

        return getByIdOrThrow(typeId);
    }

    @Transactional
    public D removeTranslation(Integer typeId, String languageCode) {
        getEntity(typeId);
        translationRepository.deleteByTypeIdAndLanguageCode(typeId, languageCode);
        return getByIdOrThrow(typeId);
    }

    @Transactional
    protected void saveTranslations(E entity, List<DT> translationDtos) {
        translationRepository.deleteByTypeId(entity.getId());

        translationDtos.stream()
                .peek(dto -> setTypeIdInTranslation(dto, entity.getId()))
                .map(dto -> mapper.toTranslationEntity(dto, entity))
                .forEach(translationRepository::save);
    }

    @Transactional(readOnly = true)
    public List<DT> getTranslations(Integer typeId) {
        return translationRepository.findByTypeId(typeId)
                .stream()
                .map(mapper::toTranslationDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<DT> getTranslation(Integer typeId, String languageCode) {
        return translationRepository.findByTypeIdAndLanguageCode(typeId, languageCode)
                .map(mapper::toTranslationDto);
    }

    protected abstract E createNewEntity();
    protected abstract DT createTranslationDto(Integer typeId, String languageCode, String translation);
    protected abstract void setTypeIdInTranslation(DT translationDto, Integer typeId);
}
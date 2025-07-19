package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.abstracts.BaseTypeDto;
import kg.edu.mathbilim.dto.abstracts.TypeTranslationDto;
import kg.edu.mathbilim.model.abstracts.BaseType;
import kg.edu.mathbilim.model.abstracts.TypeTranslation;

public interface TypeBaseMapper<
        E extends BaseType<T>,
        D extends BaseTypeDto<DT>,
        T extends TypeTranslation<E>,
        DT extends TypeTranslationDto,
        TM extends BaseTranslationMapper<T, DT>> {

    D toDto(E entity);
    E toEntity(D dto);

    default DT toTranslationDto(T entity) {
        return getTranslationMapper().toDto(entity);
    }

    default T toTranslationEntity(DT dto, E parent) {
        T entity = getTranslationMapper().toEntity(dto);
        entity.setParent(parent);
        return entity;
    }

    TM getTranslationMapper();
}
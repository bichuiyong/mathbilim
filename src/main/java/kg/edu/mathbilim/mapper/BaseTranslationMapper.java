package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.abstracts.TypeTranslationDto;
import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import org.mapstruct.Mapping;

public interface BaseTranslationMapper<T extends TypeTranslation<?>, DT extends TypeTranslationDto> {
    @Mapping(source = "id.languageCode", target = "languageCode")
    DT toDto(T entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "parent", ignore = true)
    T toEntity(DT dto);
}

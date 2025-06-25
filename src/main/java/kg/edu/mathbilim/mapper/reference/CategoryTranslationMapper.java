package kg.edu.mathbilim.mapper.reference;

import kg.edu.mathbilim.dto.reference.CategoryTranslationDto;
import kg.edu.mathbilim.mapper.BaseTranslationMapper;
import kg.edu.mathbilim.model.reference.CategoryTranslation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryTranslationMapper extends BaseTranslationMapper<CategoryTranslation, CategoryTranslationDto> {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "typeId")
    CategoryTranslationDto toDto(CategoryTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "typeId")
    @Mapping(target = "parent.id", source = "typeId")
    CategoryTranslation toEntity(CategoryTranslationDto dto);

}
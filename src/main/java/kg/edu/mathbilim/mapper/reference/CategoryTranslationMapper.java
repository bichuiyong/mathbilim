package kg.edu.mathbilim.mapper.reference;

import kg.edu.mathbilim.dto.reference.CategoryTranslationDto;
import kg.edu.mathbilim.mapper.BaseTranslationMapper;
import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.model.reference.CategoryTranslation;
import kg.edu.mathbilim.model.abstracts.TypeTranslation.TranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryTranslationMapper extends BaseTranslationMapper<CategoryTranslation, CategoryTranslationDto> {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "categoryId")
    CategoryTranslationDto toDto(CategoryTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "categoryId")
    @Mapping(target = "parent.id", source = "categoryId")
    CategoryTranslation toEntity(CategoryTranslationDto dto);
}
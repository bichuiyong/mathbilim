package kg.edu.mathbilim.mapper.reference.category;

import kg.edu.mathbilim.dto.reference.category.CategoryTranslationDto;
import kg.edu.mathbilim.model.reference.category.CategoryTranslation;
import kg.edu.mathbilim.model.reference.category.CategoryTranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryTranslationMapper {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "translation", target = "translation")
    CategoryTranslationDto toDto(CategoryTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.categoryId", source = "categoryId")
    @Mapping(target = "translation", source = "translation")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "category.categoryTranslations", ignore = true)
    CategoryTranslation toEntity(CategoryTranslationDto dto);

    @AfterMapping
    default void ensureCompositeKey(@MappingTarget CategoryTranslation entity, CategoryTranslationDto dto) {
        if (entity.getId() == null) {
            entity.setId(new CategoryTranslationId());
            entity.getId().setCategoryId(dto.getCategoryId());
            entity.getId().setLanguageCode(dto.getLanguageCode());
        }
    }
}

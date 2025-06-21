package kg.edu.mathbilim.mapper.reference;

import kg.edu.mathbilim.dto.reference.CategoryTranslationDto;
import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.model.reference.CategoryTranslation;
import kg.edu.mathbilim.model.abstracts.TypeTranslation.TranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryTranslationMapper {

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.typeId", target = "categoryId")
    @Mapping(source = "translation", target = "translation")
    CategoryTranslationDto toDto(CategoryTranslation entity);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.typeId", source = "categoryId")
    @Mapping(target = "translation", source = "translation")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "category.categoryTranslations", ignore = true)
    CategoryTranslation toEntity(CategoryTranslationDto dto);

    @AfterMapping
    default void ensureCompositeKey(@MappingTarget CategoryTranslation entity, CategoryTranslationDto dto) {
        if (entity.getId() == null) {
            entity.setId(new TranslationId());
        }
        entity.getId().setTypeId(dto.getCategoryId());
        entity.getId().setLanguageCode(dto.getLanguageCode());

        if (entity.getCategory() == null) {
            entity.setCategory(new Category());
        }
        entity.getCategory().setId(dto.getCategoryId());
    }
}
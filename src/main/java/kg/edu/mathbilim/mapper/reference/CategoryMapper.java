package kg.edu.mathbilim.mapper.reference;

import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.dto.reference.CategoryTranslationDto;
import kg.edu.mathbilim.mapper.TypeBaseMapper;
import kg.edu.mathbilim.model.reference.Category;
import kg.edu.mathbilim.model.reference.CategoryTranslation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends TypeBaseMapper<
        Category,
        CategoryDto,
        CategoryTranslation,
        CategoryTranslationDto,
        CategoryTranslationMapper> {

        @Override
        CategoryDto toDto(Category entity);

        @Override
        Category toEntity(CategoryDto dto);
        @Override
        default CategoryTranslationMapper getTranslationMapper() {
            return new CategoryTranslationMapperImpl();
        }
}

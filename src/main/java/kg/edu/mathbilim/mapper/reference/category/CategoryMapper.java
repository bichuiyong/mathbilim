package kg.edu.mathbilim.mapper.reference.category;

import kg.edu.mathbilim.dto.reference.category.CategoryDto;
import kg.edu.mathbilim.model.reference.category.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto dto);

    CategoryDto toDto(Category event);
}

package kg.edu.mathbilim.mapper.reference;

import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.model.reference.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto dto);

    CategoryDto toDto(Category category);
}

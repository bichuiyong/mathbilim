package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.CategoryDto;
import kg.edu.mathbilim.model.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    Category toEntity(CategoryDto dto);

    CategoryDto toDto(Category category);
}

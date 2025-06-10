package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.CategoryDto;
import kg.edu.mathbilim.dto.EventDto;
import kg.edu.mathbilim.model.Category;
import kg.edu.mathbilim.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto dto);

    CategoryDto toDto(Category event);
}

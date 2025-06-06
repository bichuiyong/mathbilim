package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.ContentDto;
import kg.edu.mathbilim.model.Content;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    Content toEntity(ContentDto dto);

    ContentDto toDto(Content entity);
}

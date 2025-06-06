package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.ContentBlockDto;
import kg.edu.mathbilim.model.embedded.ContentBlock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContentBlockMapper {
    ContentBlock toEntity(ContentBlockDto dto);

    ContentBlockDto toDto(ContentBlock entity);
}
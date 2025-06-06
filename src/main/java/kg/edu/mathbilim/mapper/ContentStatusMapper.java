package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.ContentStatusDto;
import kg.edu.mathbilim.model.ContentStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContentStatusMapper {
    ContentStatusDto toDto(ContentStatus entity);

    ContentStatus toEntity(ContentStatusDto dto);
}

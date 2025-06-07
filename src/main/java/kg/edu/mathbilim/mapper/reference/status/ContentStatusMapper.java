package kg.edu.mathbilim.mapper.reference.status;

import kg.edu.mathbilim.dto.reference.status.ContentStatusDto;
import kg.edu.mathbilim.model.reference.status.ContentStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContentStatusMapper {

    ContentStatus toEntity(ContentStatusDto contentStatusDto);

    ContentStatusDto toDto(ContentStatus contentStatus);
}

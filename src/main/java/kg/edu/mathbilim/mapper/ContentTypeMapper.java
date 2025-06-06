package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.ContentTypeDto;
import kg.edu.mathbilim.model.ContentType;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ContentTypeMapper {
    ContentType toEntity(ContentTypeDto contentType);

    ContentTypeDto toDto(ContentType contentType);
}

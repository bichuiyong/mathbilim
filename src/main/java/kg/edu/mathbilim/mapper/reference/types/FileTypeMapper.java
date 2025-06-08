package kg.edu.mathbilim.mapper.reference.types;

import kg.edu.mathbilim.dto.reference.types.FileTypeDto;
import kg.edu.mathbilim.model.reference.types.FileType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileTypeMapper {
    FileType toEntity(FileTypeDto dto);

    FileTypeDto toDto(FileType type);
}

package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.FileTypeDto;
import kg.edu.mathbilim.model.FileType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileTypeMapper {
    FileType toEntity(FileTypeDto dto);

    FileTypeDto toDto(FileType type);
}

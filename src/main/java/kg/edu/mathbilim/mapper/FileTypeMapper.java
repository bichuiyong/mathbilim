package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.model.FileType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileTypeMapper {
    FileType toEntity(FileDto dto);

    FileDto toDto(FileType type);
}

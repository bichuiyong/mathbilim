package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.model.File;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    File toEntity(FileDto dto);

    FileDto toDto(File file);
}

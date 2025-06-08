package kg.edu.mathbilim.service.interfaces.reference.types;

import kg.edu.mathbilim.dto.reference.types.FileTypeDto;
import kg.edu.mathbilim.model.reference.types.FileType;

import java.util.List;

public interface FileTypeService {
    List<FileTypeDto> getAllTypes();

    FileTypeDto findByMimeType(String mimeType);

    FileTypeDto findByExtension(String extension);

    FileType determineFileTypeEntity(String mimeType, String filename);

    FileTypeDto determineFileType(String mimeType, String filename);

    String getFileCategory(String mimeType);
}

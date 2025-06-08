package kg.edu.mathbilim.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kg.edu.mathbilim.enums.FileType;

@Converter(autoApply = true)
public class FileTypeConverter implements AttributeConverter<FileType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(FileType fileType) {
        if (fileType == null) {
            return null;
        }
        return fileType.getId();
    }

    @Override
    public FileType convertToEntityAttribute(Integer databaseId) {
        return FileType.fromDatabaseId(databaseId);
    }
}
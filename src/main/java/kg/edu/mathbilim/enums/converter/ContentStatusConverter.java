package kg.edu.mathbilim.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kg.edu.mathbilim.enums.ContentStatus;

@Converter(autoApply = true)
public class ContentStatusConverter implements AttributeConverter<ContentStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ContentStatus status) {
        return status != null ? status.getId() : null;
    }

    @Override
    public ContentStatus convertToEntityAttribute(Integer id) {
        return ContentStatus.fromId(id);
    }
}

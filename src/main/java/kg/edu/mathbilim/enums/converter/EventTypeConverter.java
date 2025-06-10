package kg.edu.mathbilim.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kg.edu.mathbilim.enums.EventType;

@Converter(autoApply = true)
public class EventTypeConverter implements AttributeConverter<EventType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EventType type) {
        return type != null ? type.getId() : null;
    }

    @Override
    public EventType convertToEntityAttribute(Integer id) {
        return EventType.fromId(id);
    }
}

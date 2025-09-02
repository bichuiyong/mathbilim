package kg.edu.mathbilim.components;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Converter(autoApply = true)
public class TimeConverter implements AttributeConverter<LocalDateTime, LocalDateTime> {

    private static final ZoneId UTC = ZoneId.of("UTC");

    @Override
    public LocalDateTime convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) return null;

        return attribute
                .atZone(TimeZoneFilter.getUserZone())
                .withZoneSameInstant(UTC)
                .toLocalDateTime();
    }

    @Override
    public LocalDateTime convertToEntityAttribute(LocalDateTime dbData) {
        if (dbData == null) return null;

        return dbData
                .atZone(UTC)
                .withZoneSameInstant(TimeZoneFilter.getUserZone())
                .toLocalDateTime();
    }
}

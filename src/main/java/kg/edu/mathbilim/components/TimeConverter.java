package kg.edu.mathbilim.components;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Converter(autoApply = true)
public class TimeConverter implements AttributeConverter<LocalDateTime, LocalDateTime> {
    private static final ZoneId UTC = ZoneId.of("UTC");
    private static final ZoneId BISHKEK_ZONE = ZoneId.of("Asia/Bishkek");

    @Override
    public LocalDateTime convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) return null;
        try {
            return attribute
                    .atZone(BISHKEK_ZONE)
                    .withZoneSameInstant(UTC)
                    .toLocalDateTime();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при преобразовании времени в UTC для базы данных", e);
        }
    }

    @Override
    public LocalDateTime convertToEntityAttribute(LocalDateTime dbData) {
        if (dbData == null) return null;
        try {
            return dbData
                    .atZone(UTC)
                    .withZoneSameInstant(BISHKEK_ZONE)
                    .toLocalDateTime();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при преобразовании времени из UTC в Asia/Bishkek", e);
        }
    }
}
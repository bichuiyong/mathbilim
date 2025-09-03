package kg.edu.mathbilim.components;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Converter(autoApply = true)
public class TimeConverter implements AttributeConverter<LocalDateTime, LocalDateTime> {
    private static final ZoneId UTC = ZoneId.of("UTC");
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Bishkek");

    @Override
    public LocalDateTime convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) return null;
        HttpServletRequest request;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            request = null;
        }
        ZoneId userZone = request != null ? TimeZoneFilter.getUserZone(request) : DEFAULT_ZONE;
        try {
            LocalDateTime result = attribute
                    .atZone(userZone)
                    .withZoneSameInstant(UTC)
                    .toLocalDateTime();
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public LocalDateTime convertToEntityAttribute(LocalDateTime dbData) {
        if (dbData == null) return null;
        HttpServletRequest request;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            request = null;
        }
        ZoneId userZone = request != null ? TimeZoneFilter.getUserZone(request) : DEFAULT_ZONE;
        try {
            LocalDateTime result = dbData
                    .atZone(UTC)
                    .withZoneSameInstant(userZone)
                    .toLocalDateTime();
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}
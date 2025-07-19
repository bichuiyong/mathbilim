package kg.edu.mathbilim.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kg.edu.mathbilim.enums.TestStatus;

@Converter(autoApply = true)
public class TestStatusConverter implements AttributeConverter<TestStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TestStatus status) {
        return status != null ? status.getId() : null;
    }

    @Override
    public TestStatus convertToEntityAttribute(Integer id) {
        return TestStatus.fromId(id);
    }
}

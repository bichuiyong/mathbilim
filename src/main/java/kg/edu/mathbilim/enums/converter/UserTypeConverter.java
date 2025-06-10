package kg.edu.mathbilim.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kg.edu.mathbilim.enums.UserType;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserType type) {
        return type != null ? type.getId() : null;
    }

    @Override
    public UserType convertToEntityAttribute(Integer id) {
        return UserType.fromId(id);
    }
}

package kg.edu.mathbilim.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kg.edu.mathbilim.enums.PostType;

@Converter(autoApply = true)
public class PostTypeConverter implements AttributeConverter<PostType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PostType type) {
        return type != null ? type.getId() : null;
    }

    @Override
    public PostType convertToEntityAttribute(Integer id) {
        return PostType.fromId(id);
    }
}

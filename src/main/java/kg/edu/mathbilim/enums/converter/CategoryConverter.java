package kg.edu.mathbilim.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kg.edu.mathbilim.enums.Category;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, Integer> {

    @Override
    public Integer convertToDatabaseColumn(kg.edu.mathbilim.enums.Category category) {
        return category != null ? category.getId() : null;
    }

    @Override
    public kg.edu.mathbilim.enums.Category convertToEntityAttribute(Integer id) {
        return Category.fromId(id);
    }
}

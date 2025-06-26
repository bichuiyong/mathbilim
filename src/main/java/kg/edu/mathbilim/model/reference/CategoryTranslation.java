package kg.edu.mathbilim.model.reference;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "category_translations")
@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryTranslation extends TypeTranslation<Category> {
    public CategoryTranslation() {
        super();
    }
}
package kg.edu.mathbilim.model.reference;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.BaseType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "categories")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends BaseType<CategoryTranslation> {
}

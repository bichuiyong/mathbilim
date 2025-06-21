package kg.edu.mathbilim.model.reference.category;

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
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryTranslation extends TypeTranslation {

    @EmbeddedId
    TranslationId id;

    @MapsId("typeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;
}
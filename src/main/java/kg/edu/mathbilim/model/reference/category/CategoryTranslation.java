package kg.edu.mathbilim.model.reference.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category_translations")
public class CategoryTranslation {
    @EmbeddedId
    private CategoryTranslationId id;

    @MapsId("categoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Size(max = 100)
    @NotNull
    @Column(name = "translation", nullable = false, length = 100)
    private String translation;

}
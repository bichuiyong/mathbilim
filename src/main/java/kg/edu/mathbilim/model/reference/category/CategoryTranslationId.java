package kg.edu.mathbilim.model.reference.category;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CategoryTranslationId implements Serializable {
    @Serial
    private static final long serialVersionUID = -5233130290072410590L;
    @NotNull
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Size(max = 2)
    @NotNull
    @Column(name = "language_code", nullable = false, length = 2)
    private String languageCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CategoryTranslationId entity = (CategoryTranslationId) o;
        return Objects.equals(this.languageCode, entity.languageCode) &&
                Objects.equals(this.categoryId, entity.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(languageCode, categoryId);
    }

}
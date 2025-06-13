package kg.edu.mathbilim.model.post.post_type;

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
public class PostTypeTranslationId implements Serializable {
    @Serial
    private static final long serialVersionUID = 4449632788266008457L;
    @NotNull
    @Column(name = "post_type_id", nullable = false)
    private Integer postTypeId;

    @Size(max = 2)
    @NotNull
    @Column(name = "language_code", nullable = false, length = 2)
    private String languageCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PostTypeTranslationId entity = (PostTypeTranslationId) o;
        return Objects.equals(this.postTypeId, entity.postTypeId) &&
                Objects.equals(this.languageCode, entity.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postTypeId, languageCode);
    }

}
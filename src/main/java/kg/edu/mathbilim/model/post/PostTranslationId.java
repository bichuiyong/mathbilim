package kg.edu.mathbilim.model.post;

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
public class PostTranslationId implements Serializable {
    @Serial
    private static final long serialVersionUID = -3657110886464466177L;

    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Size(max = 2)
    @NotNull
    @Column(name = "language_code", nullable = false, length = 2)
    private String languageCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PostTranslationId entity = (PostTranslationId) o;
        return Objects.equals(this.postId, entity.postId) &&
                Objects.equals(this.languageCode, entity.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, languageCode);
    }

}
package kg.edu.mathbilim.model.reference.user_type;

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
public class UserTypeTranslationId implements Serializable {
    @Serial
    private static final long serialVersionUID = -6385523859603761347L;
    @NotNull
    @Column(name = "user_type_id", nullable = false)
    private Integer userTypeId;

    @Size(max = 2)
    @NotNull
    @Column(name = "language_code", nullable = false, length = 2)
    private String languageCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserTypeTranslationId entity = (UserTypeTranslationId) o;
        return Objects.equals(this.userTypeId, entity.userTypeId) &&
                Objects.equals(this.languageCode, entity.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userTypeId, languageCode);
    }

}
package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OlympiadContactId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1814411025584242951L;
    @NotNull
    @Column(name = "olympiad_id", nullable = false)
    private Long olympiadId;

    @NotNull
    @Column(name = "contact_type_id", nullable = false)
    private Long contactTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OlympiadContactId entity = (OlympiadContactId) o;
        return Objects.equals(this.olympiadId, entity.olympiadId) &&
                Objects.equals(this.contactTypeId, entity.contactTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(olympiadId, contactTypeId);
    }

}
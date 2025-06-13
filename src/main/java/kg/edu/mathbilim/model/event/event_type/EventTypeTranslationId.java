package kg.edu.mathbilim.model.event.event_type;

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
public class EventTypeTranslationId implements Serializable {
    @Serial
    private static final long serialVersionUID = -7932573002202526055L;
    @NotNull
    @Column(name = "event_type_id", nullable = false)
    private Integer eventTypeId;

    @Size(max = 2)
    @NotNull
    @Column(name = "language_code", nullable = false, length = 2)
    private String languageCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EventTypeTranslationId entity = (EventTypeTranslationId) o;
        return Objects.equals(this.eventTypeId, entity.eventTypeId) &&
                Objects.equals(this.languageCode, entity.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventTypeId, languageCode);
    }

}
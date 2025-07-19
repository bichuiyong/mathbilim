package kg.edu.mathbilim.model.event;

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
public class EventTranslationId implements Serializable {
    @Serial
    private static final long serialVersionUID = -4336639803736443369L;

    @NotNull
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Size(max = 2)
    @NotNull
    @Column(name = "language_code", nullable = false, length = 2)
    private String languageCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EventTranslationId entity = (EventTranslationId) o;
        return Objects.equals(this.eventId, entity.eventId) &&
                Objects.equals(this.languageCode, entity.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, languageCode);
    }

}
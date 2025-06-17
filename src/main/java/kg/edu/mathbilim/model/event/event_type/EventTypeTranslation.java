package kg.edu.mathbilim.model.event.event_type;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event_type_translations")
public class EventTypeTranslation {
    @EmbeddedId
    private EventTypeTranslationId id;

    @MapsId("eventTypeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private EventType eventType;

    @Size(max = 100)
    @NotNull
    @Column(name = "translation", nullable = false, length = 100)
    private String translation;

}
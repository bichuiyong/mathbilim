package kg.edu.mathbilim.model.event;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.ContentTranslation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "event_translations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventTranslation  extends ContentTranslation {
    @EmbeddedId
    EventTranslationId id;

    @MapsId("eventId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    Event event;

}
package kg.edu.mathbilim.model.event;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.TranslationContent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event_translations")
public class EventTranslation  extends TranslationContent {
    @EmbeddedId
    private EventTranslationId id;

    @MapsId("eventId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;


}
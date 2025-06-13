package kg.edu.mathbilim.model.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event_translations")
public class EventTranslation {
    @EmbeddedId
    private EventTranslationId id;

    @MapsId("eventId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Size(max = 500)
    @NotNull
    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

}
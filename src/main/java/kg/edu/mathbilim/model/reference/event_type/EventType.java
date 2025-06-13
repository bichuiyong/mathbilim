package kg.edu.mathbilim.model.reference.event_type;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "event_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "eventType")
    private Set<EventTypeTranslation> eventTypeTranslations = new LinkedHashSet<>();
}

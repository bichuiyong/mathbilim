package kg.edu.mathbilim.model.event;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @Builder.Default
    private List<EventTypeTranslation> eventTypeTranslations = new ArrayList<>();
}

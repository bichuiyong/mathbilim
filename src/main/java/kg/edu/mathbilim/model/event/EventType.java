package kg.edu.mathbilim.model.event;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kg.edu.mathbilim.model.abstracts.BaseType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "event_types")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventType extends BaseType<EventTypeTranslation> {
}

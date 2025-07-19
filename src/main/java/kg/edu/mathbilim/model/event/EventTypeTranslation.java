package kg.edu.mathbilim.model.event;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "event_type_translations")
@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventTypeTranslation extends TypeTranslation<EventType> {
    public EventTypeTranslation() {
        super();
    }
}
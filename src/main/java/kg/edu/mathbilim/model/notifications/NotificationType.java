package kg.edu.mathbilim.model.notifications;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="notification_type")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false, name = "name")
    NotificationEnum name;
}

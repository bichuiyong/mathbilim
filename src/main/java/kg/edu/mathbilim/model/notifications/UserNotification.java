package kg.edu.mathbilim.model.notifications;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="user_notification")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="notification_type_id")
    NotificationType type;


}

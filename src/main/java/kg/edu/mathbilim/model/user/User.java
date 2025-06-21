package kg.edu.mathbilim.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.reference.Role;
import kg.edu.mathbilim.model.test.Test;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    String name;

    @Size(max = 100)
    @Column(name = "surname", length = 100)
    String surname;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    String email;

    @Size(max = 255)
    @Column(name = "password")
    String password;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "enabled", nullable = false)
    Boolean enabled;

    @Column(name = "is_email_verified")
    Boolean isEmailVerified;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    List<Test> tests = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "type_id")
    UserType type;

    @Column(name = "password_reset_token")
    String resetPasswordToken;

    @Column(name = "email_verification_token")
    String emailVerificationToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "avatar")
    File avatar;

    Long telegramId;
}
package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @Column(name = "surname", length = 100)
    private String surname;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "type_id")
    private UserType type;

    @Size(max = 2)
    @NotNull
    @ColumnDefault("'ru'")
    @Column(name = "preferred_language", nullable = false, length = 2)
    private String preferredLanguage;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "user")
    private Set<File> files = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Test> tests = new LinkedHashSet<>();

}
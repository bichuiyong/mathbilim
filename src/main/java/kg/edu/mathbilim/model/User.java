package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import lombok.*;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email",
            nullable = false,
            unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled",
            nullable = false)
    @Builder.Default
    private Boolean enabled = true;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @Column(name = "preferred_language",
            nullable = false)
    @Builder.Default
    private String preferredLanguage = "ru";

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",
            nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(name = "type_id",
            nullable = false)
    private UserType type;

    @OneToMany(mappedBy = "approvedBy")
    private List<Content> approvedByList = new ArrayList<>();
}

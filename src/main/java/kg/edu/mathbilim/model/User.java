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
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private Boolean enabled;

    private Boolean isEmailVerified;

    private String preferredLanguage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String resetPasswordToken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne
    @JoinColumn(name = "type_id")
    private UserType type;

    @OneToMany(mappedBy = "author")
    private List<User> authorList = new ArrayList<>();

    @OneToMany(mappedBy = "approvedBy")
    private List<User> approvedByList = new ArrayList<>();
}

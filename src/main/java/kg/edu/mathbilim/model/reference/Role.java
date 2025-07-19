package kg.edu.mathbilim.model.reference;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    Integer id;

    @Column(name = "name",
            unique = true,
            nullable = false)
    String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "role")
    List<User> users = new ArrayList<>();
}

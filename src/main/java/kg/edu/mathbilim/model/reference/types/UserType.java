package kg.edu.mathbilim.model.reference.types;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.User;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "user_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Integer id;

    @Column(name = "name",
            unique = true,
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "type")
    private Set<User> users = new LinkedHashSet<>();
}

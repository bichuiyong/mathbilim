package kg.edu.mathbilim.model.reference.user_type;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.User;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_types")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "userType")
    private Set<UserTypeTranslation> userTypeTranslations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "type")
    private Set<User> users = new LinkedHashSet<>();
}

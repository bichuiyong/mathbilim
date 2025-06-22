package kg.edu.mathbilim.model.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_types")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @OneToMany(mappedBy = "userType")
    List<UserTypeTranslation> userTypeTranslations = new ArrayList<>();

}

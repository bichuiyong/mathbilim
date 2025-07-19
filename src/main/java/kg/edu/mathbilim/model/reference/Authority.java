package kg.edu.mathbilim.model.reference;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    Integer id;

    @Column(name = "name",
            nullable = false,
            unique = true)
    String name;

    @ManyToMany(mappedBy = "authorities")
    List<Role> roles = new ArrayList<>();
}

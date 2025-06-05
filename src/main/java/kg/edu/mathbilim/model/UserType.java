package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import lombok.*;

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
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;
}

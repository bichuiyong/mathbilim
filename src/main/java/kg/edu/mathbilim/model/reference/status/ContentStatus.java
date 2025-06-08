package kg.edu.mathbilim.model.reference.status;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.Post;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "content_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Integer id;

    @Column(name = "name",
            nullable = false,
            unique = true)
    private String name;

    @OneToMany(mappedBy = "status")
    private Set<Post> posts = new LinkedHashSet<>();

}

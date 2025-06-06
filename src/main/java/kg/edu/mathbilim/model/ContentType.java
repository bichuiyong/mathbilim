package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "content_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Integer id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ContentType parent;

    @OneToMany(mappedBy = "parent")
    private Set<ContentType> children = new HashSet<>();
}

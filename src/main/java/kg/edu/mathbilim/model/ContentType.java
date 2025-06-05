package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ContentType parent;

    @OneToMany(mappedBy = "parent")
    private Set<ContentType> children = new HashSet<>();

    @OneToMany(mappedBy = "type")
    private List<ContentType> typeList = new ArrayList<>();
}

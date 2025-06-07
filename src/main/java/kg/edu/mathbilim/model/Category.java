package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Integer id;

    @Column(name = "name",
            nullable = false,
            unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Book> books = new LinkedHashSet<>();

    @OneToMany(mappedBy = "category")
    private Set<Test> tests = new LinkedHashSet<>();

}

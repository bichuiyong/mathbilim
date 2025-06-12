package kg.edu.mathbilim.model.reference.category;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.Book;
import kg.edu.mathbilim.model.Test;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "category")
    private Set<Book> books = new LinkedHashSet<>();

    @OneToMany(mappedBy = "category")
    private Set<CategoryTranslation> categoryTranslations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "category")
    private Set<Test> tests = new LinkedHashSet<>();

}

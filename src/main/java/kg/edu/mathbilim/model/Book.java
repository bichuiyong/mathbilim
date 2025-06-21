package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.model.abstracts.Content;
import kg.edu.mathbilim.model.reference.category.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

@Entity
@Table(name = "books")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book extends Content {
    @Size(max = 500)
    @NotNull
    @Column(name = "name", nullable = false, length = 500)
    String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "file_id", nullable = false)
    File file;

    @Size(max = 255)
    @Column(name = "authors")
    String authors;

    @Size(max = 20)
    @Column(name = "isbn", length = 20)
    String isbn;

    @Size(max = 300)
    @Column(name = "description", length = 300)
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "category_id")
    Category category;

}
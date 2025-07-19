package kg.edu.mathbilim.model.blog;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.Comment;
import kg.edu.mathbilim.model.abstracts.Content;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blogs")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Blog extends Content {

    @OneToMany(mappedBy = "blog")
    List<BlogTranslation> blogTranslations = new ArrayList<>();

    @ManyToMany(mappedBy = "blogs")
    List<Comment> comments = new ArrayList<>();
}
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Blog extends Content {
    @ManyToMany
    @JoinTable(name = "blog_comments",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "blog")
    private List<BlogTranslation> blogTranslations = new ArrayList<>();
}
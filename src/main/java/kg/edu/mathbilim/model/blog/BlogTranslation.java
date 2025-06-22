package kg.edu.mathbilim.model.blog;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.ContentTranslation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "blog_translations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogTranslation extends ContentTranslation {
    @EmbeddedId
    BlogTranslationId id;

    @MapsId("blogId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blog_id", nullable = false)
    Blog blog;
}
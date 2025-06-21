package kg.edu.mathbilim.model.blog;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.TranslationContent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "blog_translations")
public class BlogTranslation extends TranslationContent {
    @EmbeddedId
    private BlogTranslationId id;

    @MapsId("blogId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

}
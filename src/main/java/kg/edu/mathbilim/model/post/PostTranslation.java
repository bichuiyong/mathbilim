package kg.edu.mathbilim.model.post;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.TranslationContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "post_translations")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PostTranslation extends TranslationContent {
    @EmbeddedId
    private PostTranslationId id;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


}
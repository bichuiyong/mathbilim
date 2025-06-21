package kg.edu.mathbilim.model.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.model.TranslationContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.tomcat.websocket.Transformation;

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
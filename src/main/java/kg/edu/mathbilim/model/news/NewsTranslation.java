package kg.edu.mathbilim.model.news;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.model.TranslationContent;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.model.post.PostTranslationId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "news_translation")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class NewsTranslation extends TranslationContent {
    @EmbeddedId
    private NewsTranslationId id;

    @MapsId("newsId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;


}
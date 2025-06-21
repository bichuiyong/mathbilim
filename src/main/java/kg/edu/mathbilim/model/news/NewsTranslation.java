package kg.edu.mathbilim.model.news;


import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.ContentTranslation;
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
public class NewsTranslation extends ContentTranslation {
    @EmbeddedId
    private NewsTranslationId id;

    @MapsId("newsId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;


}
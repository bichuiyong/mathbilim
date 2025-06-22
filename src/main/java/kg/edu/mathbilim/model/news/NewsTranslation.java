package kg.edu.mathbilim.model.news;


import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.ContentTranslation;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "news_translation")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsTranslation extends ContentTranslation {
    @EmbeddedId
    NewsTranslationId id;

    @MapsId("newsId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "news_id", nullable = false)
    News news;


}
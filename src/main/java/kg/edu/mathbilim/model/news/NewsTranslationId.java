package kg.edu.mathbilim.model.news;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class NewsTranslationId implements Serializable {
    @Serial
    private static final long serialVersionUID = -3657110886464466177L;

    @NotNull
    @Column(name = "news_id", nullable = false)
    private Long newsId;

    @Size(max = 2)
    @NotNull
    @Column(name = "language_code", nullable = false, length = 2)
    private String languageCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NewsTranslationId entity = (NewsTranslationId) o;
        return Objects.equals(this.newsId, entity.newsId) &&
                Objects.equals(this.languageCode, entity.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsId, languageCode);
    }

}
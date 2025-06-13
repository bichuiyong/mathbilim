package kg.edu.mathbilim.model.post.post_type;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "post_type_translations")
public class PostTypeTranslation {
    @EmbeddedId
    private PostTypeTranslationId id;

    @MapsId("postTypeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_type_id", nullable = false)
    private PostType postType;

    @Size(max = 100)
    @NotNull
    @Column(name = "translation", nullable = false, length = 100)
    private String translation;

}
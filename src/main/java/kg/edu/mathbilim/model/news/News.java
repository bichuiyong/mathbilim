package kg.edu.mathbilim.model.news;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.converter.ContentStatusConverter;
import kg.edu.mathbilim.model.Content;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.post.PostTranslation;
import kg.edu.mathbilim.model.post.post_type.PostType;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "news")
@AttributeOverride(name = "updatedAt", column = @Column(name = "updated_time"))
@AttributeOverride(name = "createdAt", column = @Column(name = "created_time"))
@AssociationOverrides({
        @AssociationOverride(name = "user",
                joinColumns = @JoinColumn(name = "creator_id")),
        @AssociationOverride(name = "files",
                joinTable = @JoinTable(
                        name = "news_files",
                        joinColumns = @JoinColumn(name = "news_id"),
                        inverseJoinColumns = @JoinColumn(name = "file_id")
                ))
})
public class News extends Content {

    @ColumnDefault("0")
    @Column(name = "view_count")
    private Long viewCount;



    @OneToMany(mappedBy = "news")
    private List<NewsTranslation> newsTranslations = new ArrayList<>();

}
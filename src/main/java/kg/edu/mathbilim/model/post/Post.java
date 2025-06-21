package kg.edu.mathbilim.model.post;

import jakarta.persistence.*;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.converter.ContentStatusConverter;
import kg.edu.mathbilim.model.Content;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.model.post.post_type.PostType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
@SuperBuilder
@AssociationOverrides({
        @AssociationOverride(name = "files",
                joinTable = @JoinTable(
                        name = "post_files",
                        joinColumns = @JoinColumn(name = "post_id"),
                        inverseJoinColumns = @JoinColumn(name = "file_id")
                ))
})
public class Post extends Content {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id")
    private PostType type;


    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Convert(converter = ContentStatusConverter.class)
    @Column(name = "status_id", nullable = false)
    private ContentStatus status;


    @ColumnDefault("0")
    @Column(name = "view_count")
    private Long viewCount;

    @ColumnDefault("0")
    @Column(name = "share_count")
    private Long shareCount;


    @OneToMany(mappedBy = "post")
    private List<PostTranslation> postTranslations = new ArrayList<>();

}
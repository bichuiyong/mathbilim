package kg.edu.mathbilim.model.post;

import jakarta.persistence.*;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.converter.ContentStatusConverter;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.model.post.post_type.PostType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id")
    private PostType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Convert(converter = ContentStatusConverter.class)
    @Column(name = "status_id", nullable = false)
    private ContentStatus status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ColumnDefault("0")
    @Column(name = "view_count")
    private Long viewCount;

    @ColumnDefault("0")
    @Column(name = "share_count")
    private Long shareCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "main_image_id")
    private File mainImage;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "post_files",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @Builder.Default
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostTranslation> postTranslations = new ArrayList<>();

}
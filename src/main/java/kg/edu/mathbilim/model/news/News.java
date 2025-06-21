package kg.edu.mathbilim.model.news;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.converter.ContentStatusConverter;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.post.PostTranslation;
import kg.edu.mathbilim.model.post.post_type.PostType;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "creator_id")
    private User user;

    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_time")
    private Instant createdAt;
    @UpdateTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_time")
    private Instant updatedAt;

    @ColumnDefault("0")
    @Column(name = "view_count")
    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "main_image_id")
    private File mainImage;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "news_files",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @Builder.Default
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "news")
    private List<NewsTranslation> newsTranslations = new ArrayList<>();

}
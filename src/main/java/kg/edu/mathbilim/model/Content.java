package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.embedded.ContentBlock;
import lombok.*;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "content")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;

    @Column(name = "title",
            nullable = false)
    private String title;

    @Column(name = "slug",
            nullable = false,
            unique = true)
    private String slug;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "view_count")
    @Builder.Default
    private Long viewCount = 0L;

    @Column(name = "share_count")
    @Builder.Default
    private Long shareCount = 0L;

    @Type(JsonBinaryType.class)
    @Column(name = "content_blocks", columnDefinition = "jsonb")
    private List<ContentBlock> contentBlocks;

    @Type(JsonBinaryType.class)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "approved_by", nullable = false)
    private User approvedBy;

    @ManyToOne
    @JoinColumn(name = "category_id",
            nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "status_id",
            nullable = false)
    private ContentStatus status;

    @ManyToOne
    @JoinColumn(name = "type_id",
            nullable = false)
    private ContentType type;
}

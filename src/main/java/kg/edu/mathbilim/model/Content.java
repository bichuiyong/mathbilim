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
    private Long id;

    private String title;

    private String slug;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long viewCount;

    private Long shareCount;

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
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ContentStatus status;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ContentType type;
}

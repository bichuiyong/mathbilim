package kg.edu.mathbilim.model.abstracts;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.converter.ContentStatusConverter;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "creator_id", nullable = false)
    User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "approved_by")
    User approvedBy;

    @Convert(converter = ContentStatusConverter.class)
    @Column(name = "status_id", nullable = false)
    ContentStatus status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    Instant createdAt = Instant.now();

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    Instant updatedAt = Instant.now();

    @NotNull
    @ColumnDefault("0")
    @Column(name = "view_count", nullable = false)
    Long viewCount;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "share_count", nullable = false)
    Long shareCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "main_image_id")
    File mainImage;
}

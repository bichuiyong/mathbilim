package kg.edu.mathbilim.model.abstracts;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AdminContent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    LocalDateTime updatedAt = LocalDateTime.now();

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

    public void setStatus(ContentStatus status) {

    }



    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (viewCount == null) {
            viewCount = 0L;
        }
        if (shareCount == null) {
            shareCount = 0L;
        }
    }

}

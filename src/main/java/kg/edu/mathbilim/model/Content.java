package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.post.PostTranslation;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    protected Long id;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    protected Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    protected User user;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    protected Instant updatedAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "main_image_id")
    protected File mainImage;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    protected List<File> files = new ArrayList<>();
}

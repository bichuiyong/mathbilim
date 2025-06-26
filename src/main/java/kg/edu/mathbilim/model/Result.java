package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "results")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "file_id", nullable = false)
    File file;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "olympiad_id", nullable = false)
    Olympiad olympiad;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}

package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "olympiad_stages")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympiadStage {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "olympiad_id", nullable = false)
    Olympiad olympiad;

    @NotNull
    @Column(name = "stage_order", nullable = false)
    Integer stageOrder;

    @Column(name = "registration_start")
    LocalDate registrationStart;

    @Column(name = "registration_end")
    LocalDate registrationEnd;

    @NotNull
    @Column(name = "start_date", nullable = false)
    LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    LocalDate endDate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}

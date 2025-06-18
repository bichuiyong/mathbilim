package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "stage_results",
        uniqueConstraints = @UniqueConstraint(columnNames = {"stage_id", "user_id"}))
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StageResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id", nullable = false)
    private OlympiadStage stage;

    @Column(nullable = false)
    private Integer score;

    @Column(name = "max_score", nullable = false)
    private Integer maxScore;

    @Column(nullable = false)
    private Double percentage;

    @Column(name = "is_qualified", nullable = false)
    private Boolean isQualified = false;

    @Column(name = "rank_position")
    private Integer rankPosition;

    @Column(name = "participant_number", length = 50)
    private String participantNumber;

    @Column(name = "result_date", nullable = false)
    private LocalDate resultDate = LocalDate.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    @PreUpdate
    private void calculatePercentage() {
        if (maxScore != null && maxScore > 0 && score != null) {
            percentage = Math.round((score.doubleValue() / maxScore) * 10000) / 100.0;
        } else {
            percentage = 0.0;
        }
    }
}

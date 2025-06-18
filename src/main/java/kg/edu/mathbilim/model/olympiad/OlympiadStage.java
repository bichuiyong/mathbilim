package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "olympiad_stages",
        uniqueConstraints = @UniqueConstraint(columnNames = {"olympiad_id", "stage_order"}))
public class OlympiadStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "olympiad_id", nullable = false)
    private Olympiad olympiad;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "stage_order", nullable = false)
    private Integer stageOrder;

    @Column(name = "description")
    private String description;

    @Column(name = "registration_start")
    private LocalDate registrationStart;

    @Column(name = "registration_end")
    private LocalDate registrationEnd;

    @Column(name = "event_start_date", nullable = false)
    private LocalDate eventStartDate;

    @Column(name = "event_end_date", nullable = false)
    private LocalDate eventEndDate;

    @Column(name = "location")
    private String location;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}

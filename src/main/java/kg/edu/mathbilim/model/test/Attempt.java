package kg.edu.mathbilim.model.test;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @Column(name = "started_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;
}
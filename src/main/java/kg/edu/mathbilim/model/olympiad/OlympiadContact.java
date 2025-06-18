package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "olympiad_contacts")
public class OlympiadContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "olympiad_id", nullable = false)
    private Olympiad olympiad;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}

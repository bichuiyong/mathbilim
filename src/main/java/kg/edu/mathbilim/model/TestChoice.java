package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.embedded.TestChoiceId;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "test_choices")
public class TestChoice {
    @EmbeddedId
    private TestChoiceId id;

    @MapsId("testId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "answered_at")
    private Instant answeredAt;
}
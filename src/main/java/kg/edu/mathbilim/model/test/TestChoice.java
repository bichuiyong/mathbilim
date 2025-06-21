package kg.edu.mathbilim.model.test;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "test_choices")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestChoice {
    @EmbeddedId
    TestChoiceId id;

    @MapsId("testId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "test_id", nullable = false)
    Test test;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "answered_at")
    LocalDateTime answeredAt;
}
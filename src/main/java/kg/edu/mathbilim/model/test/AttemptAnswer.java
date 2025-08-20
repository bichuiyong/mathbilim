package kg.edu.mathbilim.model.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attempt_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttemptAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attempt_id", nullable = false)
    private Attempt attempt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "chosen_answer", length = 50)
    private String chosenAnswer;

    @Column(name = "is_correct")
    private Boolean isCorrect;
}

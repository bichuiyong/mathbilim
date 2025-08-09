package kg.edu.mathbilim.model.test;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "test_page_id", nullable = false)
    private TestPage testPage;

    private Boolean multipleChoice;

    @Column(name = "correct_answer", nullable = false, length = 200)
    private String correctAnswer;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight = BigDecimal.valueOf(1.0);

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
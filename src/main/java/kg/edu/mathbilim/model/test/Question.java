package kg.edu.mathbilim.model.test;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "test_id")
    @ManyToOne
    private Test test;

    private Integer numberOrder;

    @Column(name = "test_page_number", nullable = false)
    private Integer testPageNumber;

    private Boolean textFormat;

    @Column(name = "correct_answer", nullable = false, length = 200)
    private String correctAnswer;


    private Double weight;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
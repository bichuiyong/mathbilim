package kg.edu.mathbilim.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TestChoiceId implements Serializable {
    private static final long serialVersionUID = 645043776853512747L;
    @NotNull
    @Column(name = "test_id", nullable = false)
    private Long testId;

    @NotNull
    @Column(name = "question_number", nullable = false)
    private Integer questionNumber;

    @Size(max = 30)
    @NotNull
    @Column(name = "question_value", nullable = false, length = 30)
    private String questionValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TestChoiceId entity = (TestChoiceId) o;
        return Objects.equals(this.questionValue, entity.questionValue) &&
                Objects.equals(this.testId, entity.testId) &&
                Objects.equals(this.questionNumber, entity.questionNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionValue, testId, questionNumber);
    }

}
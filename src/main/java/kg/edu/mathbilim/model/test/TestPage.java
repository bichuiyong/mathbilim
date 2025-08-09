package kg.edu.mathbilim.model.test;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


@Entity
@Table(name = "test_pages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @Column(name = "page_number", nullable = false)
    private Integer pageNumber;
}

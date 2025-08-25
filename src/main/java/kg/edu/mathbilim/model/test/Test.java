package kg.edu.mathbilim.model.test;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.model.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    private Boolean hasLimit;

    @Column(name = "time_limit")
    private Integer timeLimit;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "test", fetch = FetchType.EAGER)
    private List<Question> questions = new ArrayList<>();

    @NotNull
    @Column(name = "deleted", nullable = false)
    @ColumnDefault("false")
    @Builder.Default
    private boolean deleted = false;
}

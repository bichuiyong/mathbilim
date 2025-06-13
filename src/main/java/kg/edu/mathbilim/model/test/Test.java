package kg.edu.mathbilim.model.test;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.enums.TestStatus;
import kg.edu.mathbilim.enums.converter.TestStatusConverter;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.model.reference.category.Category;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @Column(name = "metadata", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @ColumnDefault("0")
    @Column(name = "result")
    private Integer result;

    @NotNull
    @Column(name = "time_limit", nullable = false)
    private Integer timeLimit;

    @Convert(converter = TestStatusConverter.class)
    @Column(name = "status_id", nullable = false)
    private TestStatus status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "test")
    private List<TestChoice> testChoices = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "file_id")
    private File file;

}
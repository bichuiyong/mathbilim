package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tests")
public class Test {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "s3_link", nullable = false)
    private String s3Link;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "category_id", nullable = false)
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "status_id", nullable = false)
    private TestStatus status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "test")
    private Set<TestChoice> testChoices = new LinkedHashSet<>();

}
package kg.edu.mathbilim.model.test;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.enums.TestStatus;
import kg.edu.mathbilim.enums.converter.TestStatusConverter;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tests")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @Column(name = "metadata", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    Map<String, Object> metadata;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "started_at")
    LocalDateTime startedAt;

    @Column(name = "finished_at")
    LocalDateTime finishedAt;

    @ColumnDefault("0")
    @Column(name = "result")
    Integer result;

    @NotNull
    @Column(name = "time_limit", nullable = false)
    Integer timeLimit;

    @Convert(converter = TestStatusConverter.class)
    @Column(name = "status_id", nullable = false)
    TestStatus status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "test")
    List<TestChoice> testChoices = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "file_id")
    File file;

}
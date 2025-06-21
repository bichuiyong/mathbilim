package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import kg.edu.mathbilim.model.user.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Entity
@Table(name = "olympiads")
public class Olympiad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(name = "info")
    private String info;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OlympiadStatus status = OlympiadStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(name = "rules")
    private String rules;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "olympiad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OlympiadStage> stages = new ArrayList<>();

    @OneToMany(mappedBy = "olympiad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OlympiadContact> contactInfos = new ArrayList<>();

    @OneToMany(mappedBy = "olympiad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OlympiadOrganization> olympiadOrganizations = new ArrayList<>();

}

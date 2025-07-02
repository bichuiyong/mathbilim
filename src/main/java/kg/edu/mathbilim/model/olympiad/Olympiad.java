package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Entity
@Table(name = "olympiads")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Olympiad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(name = "info")
    String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    File image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    User creator;

    @Column(name = "rules")
    String rules;

    @Column(name = "start_date", nullable = false)
    LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    LocalDate endDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "olympiad", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OlympiadStage> stages = new ArrayList<>();

    @OneToMany(mappedBy = "olympiad", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OlympiadContact> contactInfos = new ArrayList<>();

    @OneToMany(mappedBy = "olympiad", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OlympiadOrganization> olympiadOrganizations = new ArrayList<>();
}

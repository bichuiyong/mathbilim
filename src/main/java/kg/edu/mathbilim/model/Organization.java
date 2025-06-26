package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.converter.ContentStatusConverter;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "organizations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    String name;

    @Size(max = 500)
    @NotNull
    @Column(name = "description", nullable = false, length = 500)
    String description;

    @Size(max = 255)
    @Column(name = "url")
    String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "avatar")
    File avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "creator_id")
    User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "approved_by")
    User approvedBy;

    @Convert(converter = ContentStatusConverter.class)
    @Column(name = "status_id", nullable = false)
    ContentStatus status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "organizations")
    List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OlympiadOrganization> olympiadOrganizations = new ArrayList<>();

}
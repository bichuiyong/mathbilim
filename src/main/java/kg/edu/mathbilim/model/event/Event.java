package kg.edu.mathbilim.model.event;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.converter.ContentStatusConverter;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.model.event.event_type.EventType;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private EventType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Convert(converter = ContentStatusConverter.class)
    @Column(name = "status_id", nullable = false)
    private ContentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "main_image_id")
    private File mainImage;

    @NotNull
    @Column(name = "is_offline", nullable = false)
    private Boolean isOffline;

    @ManyToMany
    @JoinTable(name = "event_files",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<File> files = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "event_organizations",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id"))
    private List<Organization> organizations = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<EventTranslation> eventTranslations = new ArrayList<>();

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @Size(max = 255)
    @Column(name = "url")
    private String url;

}
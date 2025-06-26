package kg.edu.mathbilim.model.event;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.abstracts.Content;
import kg.edu.mathbilim.model.Organization;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event extends Content {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    EventType type;

    @NotNull
    @Column(name = "is_offline", nullable = false)
    Boolean isOffline;

    @ManyToMany
    @JoinTable(name = "event_organizations",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id"))
    List<Organization> organizations = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    List<EventTranslation> eventTranslations = new ArrayList<>();

    @Size(max = 255)
    @Column(name = "address")
    String address;

    @Size(max = 255)
    @Column(name = "url")
    String url;

    @NotNull
    @Column(name = "start_date", nullable = false)
    LocalDateTime startDate;

    @Column(name = "end_date")
    LocalDateTime endDate;

    @ManyToMany
    @JoinTable(name = "event_files",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    List<File> eventFiles = new ArrayList<>();
}
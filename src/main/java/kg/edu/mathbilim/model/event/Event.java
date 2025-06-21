package kg.edu.mathbilim.model.event;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.converter.ContentStatusConverter;
import kg.edu.mathbilim.model.Content;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.model.event.event_type.EventType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@AttributeOverride(name = "updatedAt", column = @Column(name = "start_date"))
@AttributeOverride(name = "createdAt", column = @Column(name = "end_date"))
@AssociationOverrides({
        @AssociationOverride(name = "files",
                joinTable = @JoinTable(
                        name = "event_files",
                        joinColumns = @JoinColumn(name = "event_id"),
                        inverseJoinColumns = @JoinColumn(name = "file_id")
                ))
})
public class Event extends Content {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private EventType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Convert(converter = ContentStatusConverter.class)
    @Column(name = "status_id", nullable = false)
    private ContentStatus status;

    @NotNull
    @Column(name = "is_offline", nullable = false)
    private Boolean isOffline;

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
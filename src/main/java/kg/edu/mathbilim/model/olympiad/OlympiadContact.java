package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.model.ContactType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "olympiad_contacts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympiadContact {
    @EmbeddedId
    OlympiadContactId id;

    @MapsId("olympiadId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "olympiad_id", nullable = false)
    Olympiad olympiad;

    @MapsId("contactTypeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "contact_type_id", nullable = false)
    ContactType contactType;

    @Size(max = 255)
    @NotNull
    @Column(name = "info", nullable = false)
    String info;
}
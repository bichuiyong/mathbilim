package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "olympiad_contacts")
public class OlympiadContactInfo {

    @EmbeddedId
    private OlympiadContactKey id;

    @ManyToOne
    @MapsId("olympiadId")
    @JoinColumn(name = "olympiad_id", nullable = false)
    private Olympiad olympiad;

    @ManyToOne
    @MapsId("contactTypeId")
    @JoinColumn(name = "contact_type_id", nullable = false)
    private ContactType contactType;

    private String info;
}

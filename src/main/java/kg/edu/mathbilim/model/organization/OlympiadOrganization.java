package kg.edu.mathbilim.model.organization;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.Organization;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import lombok.*;

@Entity
@Table(name = "olymp_organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OlympiadOrganization {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "olympiad", column = @Column(name = "olympiad_id")),
            @AttributeOverride(name = "organization", column = @Column(name = "organization_id"))
    })
    private OlympiadOrganizationKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("olympiad")
    @JoinColumn(name = "olympiad_id", insertable = false, updatable = false)
    private Olympiad olympiad;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("organization")
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    private Organization organization;
}

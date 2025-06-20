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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "olympiad_id", nullable = false)
    private Olympiad olympiad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
}

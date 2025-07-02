package kg.edu.mathbilim.model.organization;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OlympiadOrganizationKey implements Serializable {
    private Long olympiad;

    private Long organization;
}

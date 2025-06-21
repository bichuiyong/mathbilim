package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OlympiadContactKey implements Serializable {

    @Column(name = "olympiad_id")
    private Integer olympiadId;

    @Column(name = "contact_type_id")
    private Long contactTypeId;
}

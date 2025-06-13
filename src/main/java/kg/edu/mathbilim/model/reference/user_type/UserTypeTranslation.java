package kg.edu.mathbilim.model.reference.user_type;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_type_translations")
public class UserTypeTranslation {
    @EmbeddedId
    private UserTypeTranslationId id;

    @MapsId("userTypeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_type_id", nullable = false)
    private UserType userType;

    @Size(max = 50)
    @NotNull
    @Column(name = "translation", nullable = false, length = 50)
    private String translation;

}
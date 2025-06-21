package kg.edu.mathbilim.model.user.user_type;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_type_translations")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTypeTranslation extends TypeTranslation{
    @EmbeddedId
    TranslationId id;

    @MapsId("typeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_type_id", nullable = false)
    private UserType userType;
}
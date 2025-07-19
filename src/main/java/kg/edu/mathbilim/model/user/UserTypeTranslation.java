package kg.edu.mathbilim.model.user;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTypeTranslation extends TypeTranslation<UserType> {
    public UserTypeTranslation() {
        super();
    }

//    @EmbeddedId
//    TranslationId id;
//
//    @MapsId("typeId")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_type_id", nullable = false)
//    UserType userType;
}
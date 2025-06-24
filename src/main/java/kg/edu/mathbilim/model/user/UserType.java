package kg.edu.mathbilim.model.user;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.BaseType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@Entity
@Table(name = "user_types")
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserType extends BaseType<UserTypeTranslation> {

}

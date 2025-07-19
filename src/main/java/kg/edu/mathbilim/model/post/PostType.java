package kg.edu.mathbilim.model.post;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.BaseType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post_types")
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostType extends BaseType<PostTypeTranslation> {

}

package kg.edu.mathbilim.model.post;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.abstracts.TypeTranslation;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "post_type_translations")
@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostTypeTranslation extends TypeTranslation<PostType> {
    public PostTypeTranslation() {
        super();
    }

}
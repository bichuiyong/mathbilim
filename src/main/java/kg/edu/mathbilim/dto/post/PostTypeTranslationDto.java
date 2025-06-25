package kg.edu.mathbilim.dto.post;

import kg.edu.mathbilim.dto.abstracts.TypeTranslationDto;
import kg.edu.mathbilim.model.post.Post;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostTypeTranslationDto extends TypeTranslationDto {
   public PostTypeTranslationDto() {
       super();
   }
}
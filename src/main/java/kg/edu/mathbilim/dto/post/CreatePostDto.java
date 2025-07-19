package kg.edu.mathbilim.dto.post;

import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePostDto {
    @Valid
    PostDto post;

    MultipartFile image;

    MultipartFile[] attachments;

}

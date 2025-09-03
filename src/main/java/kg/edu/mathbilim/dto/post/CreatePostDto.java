package kg.edu.mathbilim.dto.post;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.validation.annotation.AllowedExtension;
import kg.edu.mathbilim.validation.annotation.NotEmptyMultipartFile;
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

    @NotEmptyMultipartFile(message = "{blog.image.required}")
    @AllowedExtension({"jpeg", "png", "jpg"})
    MultipartFile image;

    @AllowedExtension({"jpeg", "png", "jpg"})
    MultipartFile[] attachments;
}

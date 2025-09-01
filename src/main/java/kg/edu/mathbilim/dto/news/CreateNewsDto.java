package kg.edu.mathbilim.dto.news;

import jakarta.validation.Valid;
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
public class CreateNewsDto {
    @Valid
    NewsDto news;

    @NotEmptyMultipartFile(message = "{blog.image.required}")
    @AllowedExtension({"jpg", "jpeg", "png"})
    MultipartFile image;

    @AllowedExtension({"jpg", "jpeg", "png"})
    MultipartFile[] attachments;

}

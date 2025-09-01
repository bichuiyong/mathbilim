package kg.edu.mathbilim.dto.event;

import jakarta.validation.Valid;
import kg.edu.mathbilim.validation.annotation.AllowedExtension;
import kg.edu.mathbilim.validation.annotation.NotEmptyMultipartFile;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateEventDto {
    @Valid
    EventDto event;

    @NotEmptyMultipartFile(message = "{blog.image.required}")
    @AllowedExtension({"jpg", "png", "jpeg"})
    MultipartFile image;

    @AllowedExtension({"jpg", "png", "jpeg"})
    MultipartFile[] attachments;

    List<Long> organizationIds;
}

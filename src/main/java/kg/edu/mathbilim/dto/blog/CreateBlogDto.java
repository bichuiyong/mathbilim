package kg.edu.mathbilim.dto.blog;

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
public class CreateBlogDto {
    @Valid
    BlogDto blog;
    MultipartFile image;
    MultipartFile[] attachments;
}

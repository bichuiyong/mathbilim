package kg.edu.mathbilim.dto.blog;

import jakarta.validation.Valid;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBlogDto {
    @Valid
    private BlogDto blog;
    MultipartFile image;
    MultipartFile[] attachments;
}

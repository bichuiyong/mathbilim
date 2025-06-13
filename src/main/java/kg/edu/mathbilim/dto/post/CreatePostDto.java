package kg.edu.mathbilim.dto.post;

import jakarta.validation.Valid;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePostDto {
    @Valid
    private PostDto post;

    MultipartFile image;

    MultipartFile[] attachments;

}

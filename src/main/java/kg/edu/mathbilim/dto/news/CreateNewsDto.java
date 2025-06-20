package kg.edu.mathbilim.dto.news;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.post.PostDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNewsDto {
    @Valid
    private NewsDto news;

    MultipartFile image;

    MultipartFile[] attachments;

}

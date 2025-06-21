package kg.edu.mathbilim.dto.news;

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
public class CreateNewsDto {
    @Valid
    NewsDto news;

    MultipartFile image;

    MultipartFile[] attachments;

}

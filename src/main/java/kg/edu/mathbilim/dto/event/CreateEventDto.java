package kg.edu.mathbilim.dto.event;

import jakarta.validation.Valid;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEventDto {
    @Valid
    EventDto event;

    MultipartFile image;

    MultipartFile[] attachments;

    List<Long> organizationIds;
}

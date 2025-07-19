package kg.edu.mathbilim.dto.event;

import jakarta.validation.Valid;
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

    MultipartFile image;

    MultipartFile[] attachments;

    List<Long> organizationIds;
}

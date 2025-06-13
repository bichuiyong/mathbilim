package kg.edu.mathbilim.dto.event;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.OrganizationDto;
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

    MultipartFile mainImage;

    MultipartFile[] attachments;

    List<OrganizationDto> organizations;
}

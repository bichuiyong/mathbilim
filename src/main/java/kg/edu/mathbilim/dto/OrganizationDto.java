package kg.edu.mathbilim.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.validation.annotation.AllowedExtension;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationDto {
    Long id;

    @NotBlank
    @Size(max = 55, message = "Не должно превышать 55 символов")
    String name;

    @NotBlank
    @Size(max = 500, message = "Не должно быть более 500 символов")
    String description;

    @Size(max = 255, message = "Не должно быть более 255 символов")
    String url;

    FileDto avatar;


    UserDto creator;

    @Builder.Default
    UserDto approvedBy = null;

    @AllowedExtension({"jpg", "png", "jpeg"})
    MultipartFile avatarFile;

//    @NotNull
    @Builder.Default
    ContentStatus status = ContentStatus.DRAFT;

    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    LocalDateTime updatedAt = LocalDateTime.now();
}
package kg.edu.mathbilim.dto;

import jakarta.validation.constraints.NotBlank;
import kg.edu.mathbilim.dto.user.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    Long id;

    UserDto author;

    @NotBlank(message = "Комментарий не должен быть пустым")
    String content;

    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    LocalDateTime updatedAt = LocalDateTime.now();
}
